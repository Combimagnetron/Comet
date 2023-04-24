package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import me.combimagnetron.lagoon.instance.Instance;
import org.jetbrains.annotations.Nullable;

public class ProxyBoundPlayerSyncMessage extends ProxyBoundMessage {

    public ProxyBoundPlayerSyncMessage(Instance origin, Instance target) {
        super(0x04, origin, target);
    }

    public ProxyBoundPlayerSyncMessage(byte[] bytes) {
        super(bytes);
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {

    }
}
