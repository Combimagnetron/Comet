package me.combimagnetron.lagoon.internal.network;

import me.combimagnetron.lagoon.internal.network.packet.Packet;

public interface Connection {

    void send(Packet packetHolder);

}