package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.Channels;
import me.combimagnetron.comet.instance.InstanceHandler;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.game.resourcepack.ResourcePackManager;
import me.combimagnetron.comet.service.ServiceHandler;
import me.combimagnetron.comet.user.UserHandler;
import net.kyori.adventure.audience.Audience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.UUID;

public interface CometBase<T> {

    static <T> CometBase<T> comet() {
        return (CometBase<T>) Holder.INSTANCE;
    }

    Network network();

    UserHandler<? extends Audience> users();

    ServiceHandler services();

    ResourcePackManager resourcePacks();

    UUID instanceUuid();

    InstanceHandler instances();

    Channels channels();

    Path dataFolder();

    default Logger logger() {
        return LoggerFactory.getLogger(CometBase.class);
    }

    T plugin();

    final class Holder {
        public static CometBase<?> INSTANCE = null;

    }

}
