package me.combimagnetron.comet.service;

import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.generated.DeployServiceMessage;
import me.combimagnetron.generated.StartServiceMessage;
import me.combimagnetron.generated.StopServiceMessage;

import java.util.concurrent.ConcurrentHashMap;

public class RemoteServiceHandler implements ServiceHandler {
    private final ConcurrentHashMap<Identifier, Service> map = new ConcurrentHashMap<>();
    private final CometBase<?> base;
    private final MessageChannel messageChannel;

    public RemoteServiceHandler(CometBase<?> base) {
        this.base = base;
        this.messageChannel = CometBase.comet().channels().client().channel(Identifier.of("service", "handler"));
    }

    @Override
    public Service service(Identifier identifier) {
        return map.get(identifier);
    }

    @Override
    public Service deploy(Deployment deployment, Identifier identifier) {
        messageChannel.send(DeployServiceMessage.of(identifier, deployment));
        return map.put(identifier, new DummyService(identifier, messageChannel));
    }

    private record DummyService(Identifier identifier, MessageChannel messageChannel) implements Service {

        @Override
        public void stop() {
            messageChannel.send(StopServiceMessage.of(identifier));
        }

        @Override
        public void start() {
            messageChannel.send(StartServiceMessage.of(identifier));
        }

        @Override
        public void tick() {

        }
    }

}
