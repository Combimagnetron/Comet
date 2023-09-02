package me.combimagnetron.lagoon.feature.remote;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.communication.MessageHandler;
import me.combimagnetron.lagoon.communication.MessageListener;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;
import me.combimagnetron.lagoon.instance.Platform;
import me.combimagnetron.lagoon.service.Service;
import me.combimagnetron.lagoon.service.config.StringStringParameter;

import java.io.Serializable;
import java.util.Set;

import static me.combimagnetron.lagoon.operation.Operations.async;

@MessageHandler
public abstract class RemoteFeature implements Feature, MessageListener<Message>, Serializable, Service<StringStringParameter> {
    private final MessageClient client = Comet.messageClient(MessageClient.Type.REDIS);
    private final MessageChannel channel = client.channel(Identifier.of("remote-feature", identifier().key().string()));
    private final Platform platform;
    private final Set<? extends Message> messagesInUse;

    public RemoteFeature(Platform platform, Set<? extends Message> messagesInUse) {
        this.platform = platform;
        this.messagesInUse = messagesInUse;
    }

    public boolean proxyFeature() {
        return platform.proxy();
    }

    @Override
    public void send(Message message) {
        if (!messagesInUse.contains(message)) {
            return;
        }
        onSendInternal(message);
    }

    @Override
    public void receive(Message message) {
        if (!messagesInUse.contains(message)) {
            return;
        }
        onReceiveInternal(message);
    }

    @Override
    public void intercept(Message message) { }

    abstract void onSendInternal(Message message);

    abstract void onReceiveInternal(Message message);

}
