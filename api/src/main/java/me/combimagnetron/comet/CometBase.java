package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.resourcepack.ResourcePackManager;
import me.combimagnetron.comet.service.ServiceHandler;
import me.combimagnetron.comet.user.UserHandler;
import net.kyori.adventure.audience.Audience;

import javax.inject.Singleton;
import java.util.logging.Logger;

@Singleton
public interface CometBase<T> {

    Network network();

    EventBus eventBus();

    UserHandler<? extends Audience> users();

    ServiceHandler services();

    ResourcePackManager resourcePacks();

    MessageClient messageClient();

    Logger logger();

    T plugin();

}
