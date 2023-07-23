package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.user.User;
import org.jetbrains.annotations.Nullable;

public class ProxyBoundMovePlayerMessage extends ProxyBoundMessage {
    private final User<?> user;
    private final Instance to;
    private final Instance from;

    public ProxyBoundMovePlayerMessage(byte[] data) {
        super(data);
        this.user = null;
        this.to = null;
        this.from = null;
    }

    public ProxyBoundMovePlayerMessage(User<?> user, Instance to, Instance from) {
        super(0x00, to, from);
        this.user = user;
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
        writeUUID(user.uniqueIdentifier());
        writeUUID(to.uniqueIdentifier());
        writeUUID(from.uniqueIdentifier());
    }

    public User<?> globalPlayer() {
        return user;
    }

    public Instance to() {
        return to;
    }

    public Instance from() {
        return from;
    }
}
