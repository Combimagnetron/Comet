package me.combimagnetron.comet;

import me.combimagnetron.comet.internal.network.Connection;
import me.combimagnetron.comet.internal.network.packet.Packet;
import me.combimagnetron.comet.user.User;
import me.combimagnetron.generated.UserSendMinecraftPacketMessage;
import net.kyori.adventure.audience.Audience;

public class ConnectionImpl implements Connection {
    private final User<Audience> user;

    protected ConnectionImpl(User<Audience> user) {
        this.user = user;
    }

    @Override
    public void send(Packet packetHolder) {
        byte[] data = packetHolder.write();
        Byte[] bytes = new Byte[data.length];
        for (int i = 0; i < data.length; i++) {
            bytes[i] = data[i];
        }
        user.messageChannel().send(UserSendMinecraftPacketMessage.of(bytes));
    }
}
