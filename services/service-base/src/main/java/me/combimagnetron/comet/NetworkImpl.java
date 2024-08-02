package me.combimagnetron.comet;

import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.internal.network.sniffer.Sniffer;

public class NetworkImpl implements Network {
    private final Sniffer sniffer = new Sniffer.Impl();

    @Override
    public Sniffer sniffer() {
        return sniffer;
    }
}
