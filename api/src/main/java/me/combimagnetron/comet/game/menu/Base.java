package me.combimagnetron.comet.game.menu;

import me.combimagnetron.comet.game.menu.annotation.Logic;
import me.combimagnetron.comet.game.menu.draft.Draft;
import me.combimagnetron.comet.game.menu.input.Input;
import me.combimagnetron.comet.user.User;

public class Base implements Menu {


    @Override
    public void apply(Draft draft) {

    }

    @Override
    public void open(User<?> user) {

    }

    static class Test extends Base {

        @Logic
        private void logic(Input<?> input) {
            if (input.type() == Input.Type.CURSOR_MOVE) {

            }


        }


    }

}
