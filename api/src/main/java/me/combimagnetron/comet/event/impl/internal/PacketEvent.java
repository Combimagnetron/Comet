package me.combimagnetron.comet.event.impl.internal;

import me.combimagnetron.comet.event.Event;
import me.combimagnetron.comet.internal.network.packet.Packet;

public record PacketEvent<P extends Packet>(P packet) implements Event {
    @Override
    public Class<? extends Event> eventType() {
        return PacketEvent.class;
    }
}
