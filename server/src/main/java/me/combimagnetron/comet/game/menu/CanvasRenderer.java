package me.combimagnetron.comet.game.menu;

import me.combimagnetron.comet.image.SimpleCanvas;
import me.combimagnetron.comet.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.user.User;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;

import java.util.Collection;
import java.util.List;

public interface CanvasRenderer {

    static EntityReference render(SimpleCanvas simpleCanvas, User<Player> user) {
        final Component component = simpleCanvas.render();
        final TextDisplay main = TextDisplay.nonTracked(Vector3d.vec3(0), user);
        main.text(component);
        final TextDisplay edges = TextDisplay.nonTracked(Vector3d.vec3(0), user);
        edges.text(component);
        edges.transformation(edges.transformation().scale(Vector3d.vec3(1.1)));
        return EntityReference.of(List.of(main, edges));
    }

    interface EntityReference {

        TextDisplay display(int index);

        Collection<TextDisplay> displays();

        static EntityReference of(Collection<TextDisplay> displays) {
            return new Impl(displays);
        }

        final class Impl implements EntityReference {
            private final Collection<TextDisplay> displays;

            private Impl(Collection<TextDisplay> displays) {
                this.displays = displays;
            }

            @Override
            public TextDisplay display(int index) {
                return (TextDisplay) displays.toArray()[index];
            }

            @Override
            public Collection<TextDisplay> displays() {
                return displays;
            }
        }

    }


}
