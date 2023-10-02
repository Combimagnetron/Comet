package me.combimagnetron.comet.internal.network;

import me.combimagnetron.comet.internal.network.packet.Packet;

public interface Connection {

    void send(Packet packetHolder);

}