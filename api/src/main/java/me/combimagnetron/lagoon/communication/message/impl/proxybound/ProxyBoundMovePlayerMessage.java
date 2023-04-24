package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.player.GlobalPlayer;
import org.jetbrains.annotations.Nullable;

public class ProxyBoundMovePlayerMessage extends ProxyBoundMessage {
    private final GlobalPlayer<?> globalPlayer;
    private final Instance to;
    private final Instance from;

    public ProxyBoundMovePlayerMessage(byte[] data) {
        super(data);
        this.globalPlayer = null;
        this.to = null;
        this.from = null;
    }

    public ProxyBoundMovePlayerMessage(GlobalPlayer<?> globalPlayer, Instance to, Instance from) {
        super(0x00, to, from);
        this.globalPlayer = globalPlayer;
        this.to = to;
        this.from = from;
        write();
    }

    @Override
    public @Nullable Instance origin() {
        return from;
    }

    @Override
    public void write() {
        writeUUID(globalPlayer.uniqueIdentifier());
        writeUUID(to.uniqueIdentifier());
        writeUUID(from.uniqueIdentifier());
    }

    public GlobalPlayer<?> globalPlayer() {
        return globalPlayer;
    }

    public Instance to() {
        return to;
    }

    public Instance from() {
        return from;
    }
}
