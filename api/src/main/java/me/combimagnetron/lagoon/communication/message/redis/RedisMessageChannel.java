package me.combimagnetron.lagoon.communication.message.redis;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.communication.MessageRegistry;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.communication.message.MessageRecipient;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RedisMessageChannel extends RedisPubSubAdapter<byte[], byte[]> implements MessageChannel {
    private final Set<MessageRecipient> messageRecipientSet = new HashSet<>();
    private final RedisMessageClient client;
    private final Identifier identifier;

    @Override
    public void message(byte[] channel, byte[] message){
        MessageRegistry.read(channel, message);
        LoggerFactory.getLogger(RedisMessageChannel.class).info("Received from: " + new String(channel));
    }

    protected RedisMessageChannel(RedisMessageClient client, Identifier identifier) {
        this.client = client;
        this.identifier = identifier;
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public Operation<Void> send(Message abstractCustomPacket) {
        return client.send(abstractCustomPacket, this);
    }

    @Override
    public Operation<Collection<MessageRecipient>> receipients() {
        return Operation.executable(() -> messageRecipientSet);
    }
}
