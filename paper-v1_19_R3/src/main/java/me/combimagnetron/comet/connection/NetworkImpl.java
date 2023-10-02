package me.combimagnetron.comet.connection;

import me.combimagnetron.comet.internal.menu.ChestMenu;
import me.combimagnetron.comet.internal.menu.WindowIdProvider;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.internal.network.packet.Packet;
import me.combimagnetron.comet.internal.network.packet.server.ServerClickContainer;
import me.combimagnetron.comet.internal.network.sniffer.Sniffer;

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
