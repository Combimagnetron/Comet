package me.combimagnetron.lagoon.connection;

import me.combimagnetron.lagoon.internal.menu.ChestMenu;
import me.combimagnetron.lagoon.internal.network.Network;
import me.combimagnetron.lagoon.internal.network.packet.Packet;
import me.combimagnetron.lagoon.internal.network.packet.server.ServerClickContainer;
import me.combimagnetron.lagoon.internal.network.sniffer.Sniffer;
import me.combimagnetron.lagoon.internal.menu.WindowIdProvider;

public class NetworkImpl implements Network {
    private final Sniffer sniffer = new Sniffer.Impl();
    private final DefaultListener listener = new DefaultListener();

    @Override
    public Sniffer sniffer() {
        return sniffer;
    }

    final class DefaultListener {
        private final Sniffer.Node<ServerClickContainer> node = sniffer().node("default");
        private final Sniffer.Ticket<ServerClickContainer> ticket;

        DefaultListener() {
            this.ticket = node.subscribe(Packet.Type.Server.CLICK_CONTAINER);
            ticket.receive(packet -> {
                final int id = packet.windowId();
                ChestMenu menu = WindowIdProvider.get(id);
                menu.click(packet);
            });
        }


    }

}
