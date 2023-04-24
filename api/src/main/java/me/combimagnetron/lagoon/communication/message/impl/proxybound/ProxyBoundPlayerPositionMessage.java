package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.instance.Instance;
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
        x = readDouble(); y = readDouble(); z = readDouble(); pitch = readDouble(); yaw = readDouble();
        String[] identifier = readString().split(":");
        level = Identifier.of(identifier[0], identifier[1]);
        this.playerPosition = new PlayerPosition(x, y, z, pitch, yaw, level);
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        writeDouble(playerPosition.x);
        writeDouble(playerPosition.y);
        writeDouble(playerPosition.z);
        writeDouble(playerPosition.pitch);
        writeDouble(playerPosition.yaw);
        writeString(playerPosition.level.string());
    }

    public record PlayerPosition(double x, double y, double z, double pitch, double yaw, Identifier level) {

        public static PlayerPosition of(double x, double y, double z, double pitch, double yaw, Identifier level) {
            return new PlayerPosition(x, y, z, pitch, yaw, level);
        }

    }
}
