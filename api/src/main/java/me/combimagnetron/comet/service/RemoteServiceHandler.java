package me.combimagnetron.comet.service;

import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.generated.baseservice.Entity;
import me.combimagnetron.generated.deploymentservice.StopServiceMessage;
import me.combimagnetron.generated.deploymentservice.DeployServiceMessage;
import me.combimagnetron.generated.entityservice.EntityService;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteServiceHandler implements ServiceHandler {
    private final ConcurrentHashMap<Identifier, Service> map = new ConcurrentHashMap<>();
    private final CometBase<?> base;
    private final MessageChannel messageChannel;

    public RemoteServiceHandler(CometBase<?> base) {
        this.base = base;
        this.messageChannel = CometBase.comet().channels().serviceChannel();
    }

    @Override
    public Service service(Identifier identifier) {
        return map.get(identifier);
    }

    public void test() {
        EntityService entityService = CometBase.comet().services().service(EntityService.class).stream().findFirst().orElseThrow();
        List<Entity> entities = entityService.userTrackedMobs(CometBase.comet().users().user("Combimagnetron").get().sat());

    }

    @Override
    public <T extends Service> Collection<T> service(Class<T> service) {
        return map.values().stream().filter(service1 -> service1.getClass() == service).map(service::cast).toList();
    }

    @Override
    public Service deploy(Deployment deployment, Identifier identifier) {
        messageChannel.send(DeployServiceMessage.of(((Deployment.Impl)deployment).satellite()));
        return map.put(identifier, new DummyService(identifier, messageChannel, deployment));
    }

    private record DummyService(Identifier identifier, MessageChannel messageChannel, Deployment deployment) implements Service {

        @Override
        public void stop() {
            messageChannel.send(StopServiceMessage.of(identifier));
        }

        @Override
        public void start() {
            messageChannel.send(DeployServiceMessage.of(((Deployment.Impl) deployment).satellite()));
        }

        @Override
        public void tick() {

        }

    }

}
