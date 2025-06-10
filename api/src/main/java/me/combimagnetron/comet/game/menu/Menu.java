package me.combimagnetron.comet.game.menu;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.event.impl.internal.PacketEvent;
import me.combimagnetron.comet.game.menu.draft.Draft;
import me.combimagnetron.comet.game.menu.draft.Edit;
import me.combimagnetron.comet.game.menu.element.Element;
import me.combimagnetron.comet.game.menu.element.Position;
import me.combimagnetron.comet.game.menu.element.div.Div;
import me.combimagnetron.comet.game.menu.element.impl.TextElement;
import me.combimagnetron.comet.game.menu.style.ColorStyle;
import me.combimagnetron.comet.game.menu.style.Style;
import me.combimagnetron.comet.game.menu.style.Text;
import me.combimagnetron.comet.image.Canvas;
import me.combimagnetron.comet.image.Color;
import me.combimagnetron.comet.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.internal.network.packet.client.ClientTeleportEntity;
import me.combimagnetron.comet.internal.network.packet.server.ServerSetPlayerRotation;
import me.combimagnetron.comet.internal.network.sniffer.Sniffer;
import me.combimagnetron.comet.user.User;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;


public interface Menu {

    Menu apply(Draft draft);

    void open(User<?> user);

    Menu div(Div div);

    abstract class Impl implements Menu {
        private final User<?> viewer;
        private final HashMap<Identifier, Div> divHashMap = new HashMap<>();
        private final Position.UnitPosition cursor = Position.unit(0.0625);
        private final TextDisplay cursorDisplay = TextDisplay.textDisplay(Vector3d.vec3(0));
        private Pos2D lastInput = Pos2D.of(0, 0);

        public Impl(User<?> viewer) {
            this.viewer = viewer;
            initListener();
        }

        private void initListener() {
            EventBus.packet(ServerSetPlayerRotation.class, (event) -> {
                float yaw = event.packet().yaw();
                float pitch = -event.packet().pitch();
                Pos2D input = lastInput.sub(yaw, pitch).div(20);
                viewer.connection().send(ClientTeleportEntity.teleportEntity(cursorDisplay, cursorDisplay.position(), input, false));
            });
        }

        @Override
        public Menu apply(Draft draft) {
            Draft.Impl draftImpl = (Draft.Impl) draft;
            for (Edit<?> edit : draftImpl.edits()) {
                if (edit.type() == Div.class) {
                    Edit<Div> divEdit = (Edit<Div>) edit;
                    Div div = divHashMap.get(edit.identifier());
                    for (Function<Div, Div> draftEdit : divEdit.edits()) {
                        div = draftEdit.apply(div);
                    }
                    divHashMap.put(edit.identifier(), div);
                } else if (edit.type() == Element.class) {
                    Edit<Element> elementEdit = (Edit<Element>) edit;
                    Element element = divHashMap.get(edit.identifier()).elements().stream().filter(e -> e.identifier().equals(edit.identifier())).findFirst().orElse(null);
                    for (Function<Element, Element> draftEdit : elementEdit.edits()) {
                        element = draftEdit.apply(element);
                    }
                    Div div = divHashMap.get(edit.identifier());
                    div.remove(edit.identifier());
                    div.add(element);
                    divHashMap.put(edit.identifier(), div);
                }
            }
            return this;
        }

        public Canvas render() {
            Canvas image = Canvas.image(Pos2D.of(512, 256));
            image.fill(Pos2D.of(0,0), Pos2D.of(512, 256), Color.of(9, 10, 20));
            for (Div div : divHashMap.values()) {
                image = div.render(image);
            }
            return image;
        }

        @Override
        public Menu div(Div div) {
            divHashMap.put(div.identifier(), div);
            return this;
        }

    }

}
