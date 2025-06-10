package me.combimagnetron.comet.communication.message.redis;

import io.lettuce.core.pubsub.RedisPubSubAdapter;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.communication.message.MessageRecipient;
import me.combimagnetron.comet.data.Identifier;

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

}
