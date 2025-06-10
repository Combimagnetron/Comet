package me.combimagnetron.comet.game.remote;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.communication.MessageHandler;
import me.combimagnetron.comet.communication.MessageListener;
import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.game.Feature;
import me.combimagnetron.comet.instance.Platform;
import me.combimagnetron.comet.service.Service;
import me.combimagnetron.comet.data.Identifier;

import java.io.Serializable;
import java.util.Set;

@MessageHandler
public abstract class RemoteFeature implements Feature, MessageListener<Message>, Serializable, Service {
    private final MessageClient client = CometBase.comet().channels().client();
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
