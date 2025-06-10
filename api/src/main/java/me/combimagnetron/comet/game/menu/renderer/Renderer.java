package me.combimagnetron.comet.game.menu.renderer;

import me.combimagnetron.comet.game.menu.Menu;
import me.combimagnetron.comet.user.User;

public interface Renderer {

    Renderer render(User<?> user, Menu menu);

    static Renderer chest() {
        return new ChestRenderer();
    }

    static Renderer entity() {
        return new EntityRenderer();
    }

    class ChestRenderer implements Renderer {

        @Override
        public Renderer render(User<?> user, Menu menu) {
            return this;
        }
    }

    class EntityRenderer implements Renderer {

        @Override
        public Renderer render(User<?> user, Menu menu) {
            return this;
        }
    }

}
