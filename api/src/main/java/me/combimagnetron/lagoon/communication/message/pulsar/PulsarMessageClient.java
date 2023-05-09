package me.combimagnetron.lagoon.communication.message.pulsar;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.communication.ProtocolCallback;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.HashSet;
import java.util.Set;

public class PulsarMessageClient implements MessageClient {
    private final HashSet<MessageChannel> channels = new HashSet<>();
    private final PulsarClient client;

    protected PulsarMessageClient(String connectionString) throws PulsarClientException {
        this.client = PulsarClient.builder().serviceUrl(connectionString).build();
    }

    public static PulsarMessageClient of(String connectionString) {
        try {
            return new PulsarMessageClient(connectionString);
        } catch (PulsarClientException e) {
            throw new RuntimeException(e);
        }
    }

    protected PulsarClient client() {
        return client;
    }

    @Override
    public Operation<Set<MessageChannel>> channels() {
        return Operation.executable(() -> channels);
    }

    @Override
    public Operation<Void> terminate() {
        return Operation.simple(() -> client.closeAsync().complete(null));
    }

    @Override
    public Operation<Void> send(Message packet, MessageChannel channel) {
        return Operation.simple(() -> channel.send(packet));
    }

    @Override
    public Operation<Void> send(Message packet, MessageChannel channel, ProtocolCallback callback) {
        return Operation.simple(() -> {
            channel.send(packet);
            callback.onSucces();
        });
    }

    @Override
    public Operation<Void> registerChannel(MessageChannel channel) {
        return Operation.simple(() -> registerChannelInternal(channel));
    }

    @Override
    public Operation<MessageChannel> channel(Identifier identifier) {
        return Operation.executable(() -> {
            try {
                return channels.stream().filter(channel -> channel.identifier() == identifier).findFirst().orElse(createChannel(identifier));
            } catch (PulsarClientException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void registerChannelInternal(MessageChannel channel) {
        if (channel instanceof PulsarMessageChannel) {
            this.channels.add(channel);
        }
    }

    private MessageChannel createChannel(Identifier identifier) throws PulsarClientException {
        PulsarMessageChannel channel = new PulsarMessageChannel(this, identifier);
        registerChannelInternal(channel);
        return channel;
    }
}
