package me.combimagnetron.lagoon.service;

import me.combimagnetron.generated.DeployServiceMessage;
import me.combimagnetron.generated.StartServiceMessage;
import me.combimagnetron.generated.StopServiceMessage;
import me.combimagnetron.lagoon.CometBase;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.data.Identifier;

import javax.inject.Inject;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteServiceHandler implements ServiceHandler {
    private final ConcurrentHashMap<Identifier, Service> map = new ConcurrentHashMap<>();
    private final CometBase<?> base;
    private final MessageChannel messageChannel;

    @Inject
    public RemoteServiceHandler(CometBase<?> base) {
        this.base = base;
        this.messageChannel = base.messageClient().channel(Identifier.of("service", "handler"));
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
    }

}
