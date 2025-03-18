package me.combimagnetron.comet.game.menu.style;

import me.combimagnetron.comet.game.menu.Editable;
import me.combimagnetron.comet.game.menu.element.Position;
import me.combimagnetron.comet.image.Canvas;

public interface Style<T> extends Editable {

    Canvas edit(Canvas canvas, Position position, T t);

    static ColorStyle color() {
        return new ColorStyle();
    }

}
