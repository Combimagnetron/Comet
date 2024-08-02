package me.combimagnetron.comet.game.menu.element;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.Canvas;
import me.combimagnetron.comet.game.menu.Editable;

public interface Element extends Editable {

    Identifier identifier();

    Canvas canvas();

    Position position();

}
