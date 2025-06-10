package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.Channels;
import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.data.database.Database;
import me.combimagnetron.comet.data.storage.StorageProvider;
import me.combimagnetron.comet.event.Dispatcher;
import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.instance.InstanceHandler;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.game.resourcepack.ResourcePackManager;
import me.combimagnetron.comet.service.Deployment;
import me.combimagnetron.comet.service.RemoteServiceHandler;
import me.combimagnetron.comet.service.Service;
import me.combimagnetron.comet.service.ServiceHandler;
import me.combimagnetron.comet.user.UserHandler;
import me.combimagnetron.generated.deploymenthealthservice.InitialInstanceHeartbeatMessage;

import java.nio.file.Path;
import java.util.UUID;

public class CometBaseImpl<T extends Service> implements CometBase<T> {
    private final UUID instanceUuid = UUID.randomUUID();
    private final HeartbeatDispatcher heartbeatDispatcher = new HeartbeatDispatcher();
    private final MessageClient messageClient = MessageClient.redis("redis://localhost/");
    private final RemoteUserHandlerImpl userHandler = new RemoteUserHandlerImpl();
    private final T service;
    private final NetworkImpl network = new NetworkImpl();
    private final RemoteServiceHandler serviceHandler = new RemoteServiceHandler(this);

    public CometBaseImpl(T service) {
        this.service = service;
        channels().serviceChannel().send(InitialInstanceHeartbeatMessage.of(instanceUuid, ((Deployment.Impl) (service.deployment())).satellite()));
    }

    @Override
    public Network network() {
        return network;
    }

    @Override
    public UserHandler<? extends net.kyori.adventure.audience.Audience> users() {
        return userHandler;
    }

    @Override
    public ServiceHandler services() {
        return serviceHandler;
    }

    public UUID instanceUuid() {
        return instanceUuid;
    }

    @Override
    public ResourcePackManager resourcePacks() {
        return null;
    }

    @Override
    public InstanceHandler instances() {
        return null;
    }

    @Override
    public Channels channels() {
        return new Channels(messageClient.channel(Identifier.of("service")), messageClient.channel(Identifier.of("chat")), messageClient);
    }

    @Override
    public Path dataFolder() {
        return Path.of("");
    }

    @Override
    public StorageProvider storageProvider() {
        return null;
    }

    @Override
    public Database database() {
        return null;
    }

    @Override
    public T plugin() {
        return service;
    }
}
