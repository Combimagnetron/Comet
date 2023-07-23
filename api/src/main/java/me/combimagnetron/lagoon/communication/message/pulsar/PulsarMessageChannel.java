package me.combimagnetron.lagoon.communication.message.pulsar;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.communication.MessageRegistry;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.communication.message.MessageRecipient;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class PulsarMessageChannel extends Thread implements MessageChannel {
    private final PulsarMessageClient client;
    private final Identifier identifier;
    private final Producer<byte[]> producer;
    private final Consumer<byte[]> consumer;

    protected PulsarMessageChannel(PulsarMessageClient client, Identifier identifier) throws PulsarClientException {
        this.client = client;
        this.identifier = identifier;
        this.producer = client.client().newProducer().producerName(identifier.string()).topic(identifier().string()).create();
        this.consumer = client.client().newConsumer().consumerName(identifier.string()).topic(identifier().string()).subscribe();
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public void run() {
        try {
            while (!client.client().isClosed()) {
                org.apache.pulsar.client.api.Message<byte[]> message = this.consumer.receive(500, TimeUnit.MILLISECONDS);
                if (message == null) continue;
                MessageRegistry.read(message.getTopicName().getBytes(), message.getData());
            }
            this.close();
        } catch (PulsarClientException exception) {
            exception.printStackTrace();
        }
    }

    public void close() throws PulsarClientException {
        if (this.consumer.isConnected()) this.consumer.close();
        if (this.producer.isConnected()) this.producer.close();
    }

    @Override
    public Operation<Void> send(Message abstractCustomPacket) {
        return null;
    }

    @Override
    public Operation<Collection<MessageRecipient>> recipients() {
        return null;
    }

    @Override
    public Operation<Void> awaitMessage(Class<? extends Message> type, java.util.function.Consumer<Message> execute) {
        return null;
    }
}
