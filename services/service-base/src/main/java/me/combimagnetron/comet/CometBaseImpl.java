package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.Channels;
import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.resourcepack.ResourcePackManager;
import me.combimagnetron.comet.service.Service;
import me.combimagnetron.comet.service.ServiceHandler;
import me.combimagnetron.comet.user.UserHandler;
import org.slf4j.Logger;

import java.nio.file.Path;

public class CometBaseImpl<T extends Service> implements CometBase<T> {
    private final T service;
    private final NetworkImpl network = new NetworkImpl();

    public CometBaseImpl(T service) {
        this.service = service;
    }

    @Override
    public Network network() {
        return network;
    }

    @Override
    public EventBus eventBus() {
        return null;
    }

    @Override
    public UserHandler<? extends net.kyori.adventure.audience.Audience> users() {
        return null;
    }

    @Override
    public ServiceHandler services() {
        return null;
    }

    @Override
    public ResourcePackManager resourcePacks() {
        return null;
    }

    @Override
    public Channels channels() {
        return null;
    }

    @Override
    public Path dataFolder() {
        return null;
    }

    @Override
    public T plugin() {
        return service;
    }
}
