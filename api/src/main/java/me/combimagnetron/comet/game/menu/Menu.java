package me.combimagnetron.comet.game.menu;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.game.menu.draft.Draft;
import me.combimagnetron.comet.internal.network.packet.server.ServerSetPlayerRotation;
import me.combimagnetron.comet.internal.network.sniffer.Sniffer;
import me.combimagnetron.comet.user.User;


public interface Menu {

    void apply(Draft draft);

    void open(User<?> user);

    final class Impl implements Menu {
        private final Sniffer.Node<ServerSetPlayerRotation> snifferNode = CometBase.comet().network().sniffer().node("a");

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
