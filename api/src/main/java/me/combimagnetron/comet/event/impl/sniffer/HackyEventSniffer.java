package me.combimagnetron.comet.event.impl.sniffer;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.internal.network.packet.Packet;
import me.combimagnetron.comet.internal.network.sniffer.Sniffer;

public class HackyEventSniffer {

    public HackyEventSniffer(CometBase<?> cometBase) {
        init(cometBase);
    }

    private void init(CometBase<?> cometBase) {
        Sniffer.Node<Packet> node = cometBase.network().sniffer().node("hackywackyshit");
        node.subscribe(null);
    }

}
