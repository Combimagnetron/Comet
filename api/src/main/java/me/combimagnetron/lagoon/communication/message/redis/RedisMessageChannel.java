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
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

public class RedisMessageChannel extends RedisPubSubAdapter<byte[], byte[]> implements MessageChannel {
    private final Set<MessageRecipient> messageRecipientSet = new HashSet<>();
    private final Collection<Message> history = new LinkedHashSet<>();
    private final RedisMessageClient client;
    private final Identifier identifier;
    private Message lastMessage;

    @Override
    public void message(byte[] channel, byte[] message) {
        Message received = MessageRegistry.read(channel, message);
        this.history.add(received);
        lastMessage = received;
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
    public Operation<Collection<MessageRecipient>> recipients() {
        return Operation.executable(() -> messageRecipientSet);
    }

    @Override
    public Operation<Void> awaitMessage(Class<? extends Message> type, Consumer<Message> execute) {
        return Operation.await(() -> {
            execute.accept(lastMessage);
            return null;
        }, () -> {
            return this.lastMessage.getClass().getName().equals(type.getName());
        });
    }
}
