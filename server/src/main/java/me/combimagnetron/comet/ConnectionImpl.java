package me.combimagnetron.comet;

import me.combimagnetron.comet.internal.network.Connection;
import me.combimagnetron.comet.internal.network.packet.Packet;
import me.combimagnetron.comet.user.User;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerSocketConnection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class ConnectionImpl implements Connection {
    private final Player player;

    public ConnectionImpl(User<Player> user) {
        this.player = user.platformSpecificPlayer();
    }

    @Override
    public void send(Packet packetHolder) {
        if (player.getPlayerConnection() instanceof PlayerSocketConnection connection) {
            try {
                PlayerSocketConnection socketConnection = (PlayerSocketConnection) player.getPlayerConnection();
                final byte[] bytes = packetHolder.write();
                Method method = socketConnection.getClass().getDeclaredMethod("writeBufferSync0", ByteBuffer.class, int.class, int.class);
                method.setAccessible(true);
                method.invoke(socketConnection, ByteBuffer.wrap(bytes), 0, bytes.length);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
