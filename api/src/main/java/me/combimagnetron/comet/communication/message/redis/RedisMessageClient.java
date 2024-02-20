package me.combimagnetron.comet.communication.message.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import io.netty.util.AsciiString;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.communication.ProtocolCallback;
import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.data.Identifier;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisMessageClient implements MessageClient {
    private final HashSet<MessageChannel> channels = new HashSet<>();
    private final ExecutorService executor;
    private final RedisClient redisClient;
    private final StatefulRedisConnection<byte[],byte[]> pubSub;

    protected RedisMessageClient(String host, int port, String password) {
        this.executor = Executors.newSingleThreadExecutor();
        this.redisClient = RedisClient.create(RedisURI.builder().withHost(host).withAuthentication("", password).withDatabase(0).withTimeout(Duration.ofSeconds(30000)).build());
        this.pubSub = redisClient.connectPubSub(new ByteArrayCodec());
    }

    protected RedisMessageClient(String uri) {
        this.executor = Executors.newSingleThreadExecutor();
        this.redisClient = RedisClient.create(uri);
        this.pubSub = redisClient.connectPubSub(new ByteArrayCodec());
    }

    public static RedisMessageClient of(String host, int port, String password) {
        return new RedisMessageClient(host, port, password);
    }

    public static RedisMessageClient of(String uri) {
        return new RedisMessageClient(uri);
    }

    @Override
    public Set<MessageChannel> channels() {
        return channels;
    }

    @Override
    public void terminate() {
        pubSub.close();
        redisClient.shutdown();
        executor.shutdown();
    }

    @Override
    public void post(Message packet, MessageChannel channel) {
        pubSub.sync().publish(channel.identifier().string().getBytes(), packet.buffer().bytes());
    }

    @Override
    public void post(Message packet, MessageChannel channel, ProtocolCallback callback) {
        executor.execute(() -> pubSub.sync().publish(channel.identifier().string().getBytes(), packet.buffer().bytes()));
        callback.onSucces();
    }

    @Override
    public void registerChannel(MessageChannel channel) {
        registerChannelInternal(channel);
    }

    @Override
    public MessageChannel channel(Identifier identifier) {
        return channels.stream().filter(channel -> channel.identifier() == identifier).findFirst().orElse(createChannel(identifier));
    }

    private void registerChannelInternal(MessageChannel channel) {
        if (channel instanceof RedisMessageChannel messageChannel) {
            StatefulRedisPubSubConnection<byte[],byte[]> connection = redisClient.connectPubSub(new ByteArrayCodec());
            connection.addListener(messageChannel);
            RedisPubSubCommands<byte[],byte[]> pubSubCommands = connection.sync();
            pubSubCommands.subscribe(messageChannel.identifier().string().getBytes());
            this.channels.add(channel);
        }
    }

    private MessageChannel createChannel(Identifier identifier) {
        RedisMessageChannel channel = new RedisMessageChannel(this, identifier);
        registerChannelInternal(channel);
        return channel;
    }


}
