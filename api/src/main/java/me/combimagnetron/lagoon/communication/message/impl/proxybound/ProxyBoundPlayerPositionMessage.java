package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import org.jetbrains.annotations.Nullable;

public class ProxyBoundPlayerPositionMessage extends ProxyBoundMessage {
    private final PlayerPosition playerPosition;

    public ProxyBoundPlayerPositionMessage(Instance origin, Instance target, PlayerPosition playerPosition) {
        super(0x03, origin, target);
        this.playerPosition = playerPosition;
    }

    public ProxyBoundPlayerPositionMessage(byte[] bytes) {
        super(bytes);
        double x, y, z, pitch, yaw;
        Identifier level;
        x = read(ByteBuffer.Adapter.DOUBLE); y = read(ByteBuffer.Adapter.DOUBLE); z = read(ByteBuffer.Adapter.DOUBLE); pitch = read(ByteBuffer.Adapter.DOUBLE); yaw = read(ByteBuffer.Adapter.DOUBLE);
        String[] identifier = read(ByteBuffer.Adapter.STRING).split(":");
        level = Identifier.of(identifier[0], identifier[1]);
        this.playerPosition = new PlayerPosition(x, y, z, pitch, yaw, level);
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        write(ByteBuffer.Adapter.DOUBLE, playerPosition.x);
        write(ByteBuffer.Adapter.DOUBLE, playerPosition.y);
        write(ByteBuffer.Adapter.DOUBLE, playerPosition.z);
        write(ByteBuffer.Adapter.DOUBLE, playerPosition.pitch);
        write(ByteBuffer.Adapter.DOUBLE, playerPosition.yaw);
        write(ByteBuffer.Adapter.STRING, playerPosition.level.string());
    }

    public record PlayerPosition(double x, double y, double z, double pitch, double yaw, Identifier level) {

        public static PlayerPosition of(double x, double y, double z, double pitch, double yaw, Identifier level) {
            return new PlayerPosition(x, y, z, pitch, yaw, level);
        }

    }
}
