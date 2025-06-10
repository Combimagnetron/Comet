package me.combimagnetron.comet.game.menu;

import me.combimagnetron.comet.game.menu.annotation.Logic;
import me.combimagnetron.comet.game.menu.draft.Draft;
import me.combimagnetron.comet.game.menu.element.div.Div;
import me.combimagnetron.comet.game.menu.input.Input;
import me.combimagnetron.comet.user.User;

public class Base implements Menu {


    @Override
    public Menu apply(Draft draft) {
        return null;
    }

    @Override
    public void open(User<?> user) {

    }

    @Override
    public Menu div(Div div) {
        return null;
    }

    static class Test extends Base {

        @Logic
        private void logic(Input<?> input) {
            if (input.type() == Input.Type.CURSOR_MOVE) {

            }


        }


    }

}
