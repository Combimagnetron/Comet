package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.Channels;
import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.resourcepack.ResourcePackManager;
import me.combimagnetron.comet.service.ServiceHandler;
import me.combimagnetron.comet.user.UserHandler;
import net.kyori.adventure.audience.Audience;

import java.nio.file.Path;
import java.util.logging.Logger;

public interface CometBase<T> {

    static <T> CometBase<T> comet() {
        return (CometBase<T>) Holder.INSTANCE;
    }

    Network network();

    EventBus eventBus();

    UserHandler<? extends Audience> users();

    ServiceHandler services();

    ResourcePackManager resourcePacks();

    Channels channels();

    Path dataFolder();

    Logger logger();

    T plugin();

    final class Holder {
        public static CometBase<?> INSTANCE = null;

    }

}
