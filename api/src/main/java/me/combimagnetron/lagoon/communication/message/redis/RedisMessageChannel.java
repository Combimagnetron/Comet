package me.combimagnetron.lagoon.communication.message.redis;

import io.lettuce.core.pubsub.RedisPubSubAdapter;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.communication.message.MessageRecipient;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;

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
        Message received = null;//MessageRegistry.read(channel, message);
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
    public void send(Message message) {
        client.post(message, this);
    }

    @Override
    public Collection<MessageRecipient> recipients() {
        return messageRecipientSet;
    }

    @Override
    public Operation<Void> await(Class<? extends Message> type, Consumer<Message> execute) {
        return Operation.await(() -> {
            execute.accept(lastMessage);
            return null;
        }, () -> this.lastMessage.getClass().getName().equals(type.getName()));
    }
}
