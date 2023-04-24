package me.combimagnetron.lagoon.communication.message.redis;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.communication.ProtocolCallback;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import io.netty.util.AsciiString;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class RedisMessageClient implements MessageClient {
    private final HashSet<MessageChannel> channels = new HashSet<>();
    private final ExecutorService executor;
    private final RedisClient redisClient;
    private final StatefulRedisConnection<byte[],byte[]> pubSub;

    protected RedisMessageClient(String host, int port, String password) {
        this.executor = Executors.newSingleThreadExecutor();
        this.redisClient = RedisClient.create(RedisURI.builder().withHost(host).withPort(port).withPassword(new AsciiString(password)).withDatabase(0).withTimeout(Duration.ofSeconds(30000)).build());
        this.pubSub = redisClient.connectPubSub(new ByteArrayCodec());
    }

    public static RedisMessageClient of(String host, int port, String password) {
        return new RedisMessageClient(host, port, password);
    }

    @Override
    public Operation<Set<MessageChannel>> channels() {
        return Operation.executable(() -> channels);
    }

    @Override
    public Operation<Void> terminate() {
        return Operation.simple(() -> {
            pubSub.close();
            redisClient.shutdown();
            executor.shutdown();
        });
    }

    @Override
    public Operation<Void> send(Message packet, MessageChannel channel) {
        return Operation.simple(() -> pubSub.sync().publish(channel.identifier().string().getBytes(), packet.toBytes()));
    }

    @Override
    public Operation<Void> send(Message packet, MessageChannel channel, ProtocolCallback callback) {
        return Operation.simple(() -> {
            executor.execute(() -> pubSub.sync().publish(channel.identifier().string().getBytes(), packet.toBytes()));
            callback.onSucces();
        });
    }

    @Override
    public Operation<Void> registerChannel(MessageChannel channel) {
        return Operation.simple(() -> registerChannelInternal(channel));
    }

    @Override
    public Operation<MessageChannel> channel(Identifier identifier) {
        return Operation.executable(() -> channels.stream().filter(channel -> channel.identifier() == identifier).findFirst().orElse(createChannel(identifier)));
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
