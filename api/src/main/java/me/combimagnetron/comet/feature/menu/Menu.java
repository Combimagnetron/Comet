package me.combimagnetron.comet.feature.menu;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.feature.menu.draft.Draft;
import me.combimagnetron.comet.internal.network.packet.Packet;
import me.combimagnetron.comet.internal.network.packet.server.ServerSetPlayerRotation;
import me.combimagnetron.comet.internal.network.sniffer.Sniffer;
import me.combimagnetron.comet.user.User;

import javax.inject.Inject;

public interface Menu {

    void apply(Draft draft);

    void open(User<?> user);

    final class Impl implements Menu {
        @Inject
        CometBase<?> cometBase;
        private final Sniffer.Node<ServerSetPlayerRotation> snifferNode = cometBase.network().sniffer().node("a");

        private Impl() {

        }

        @Override
        public void apply(Draft draft) {

        }

        @Override
        public void open(User<?> user) {

        }

    }

}
