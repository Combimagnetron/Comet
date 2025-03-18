package me.combimagnetron.comet.game.menu.element;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.element.impl.ButtonElement;
import me.combimagnetron.comet.game.menu.element.impl.TextElement;
import me.combimagnetron.comet.game.menu.style.Style;
import me.combimagnetron.comet.game.menu.style.Text;
import me.combimagnetron.comet.image.Canvas;
import me.combimagnetron.comet.game.menu.Editable;

public interface Element extends Editable {

    Identifier identifier();

    Canvas canvas();

    Position position();

    Element position(Position pos);

    <T> Element style(Style<T> style, Position pos2D, T t);

    <T> Element style(Style<T> style, T t);

}
