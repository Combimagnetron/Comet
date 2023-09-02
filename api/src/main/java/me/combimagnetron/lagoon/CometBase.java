package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.event.EventBus;
import me.combimagnetron.lagoon.internal.network.Network;
import me.combimagnetron.lagoon.resourcepack.ResourcePackManager;
import me.combimagnetron.lagoon.service.ServiceHandler;
import me.combimagnetron.lagoon.user.UserHandler;
import net.kyori.adventure.audience.Audience;

public interface CometBase<T> {

    Network network();

    EventBus eventBus();

    UserHandler<? extends Audience> users();

    ServiceHandler services();

    ResourcePackManager resourcePacks();

    MessageClient messageClient();

    T plugin();

}
