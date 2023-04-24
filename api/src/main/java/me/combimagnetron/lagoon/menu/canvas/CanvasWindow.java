package me.combimagnetron.lagoon.menu.canvas;

import me.combimagnetron.lagoon.menu.advanced.Cursor;
import me.combimagnetron.lagoon.menu.Pos2D;
import me.combimagnetron.lagoon.menu.canvas.element.Element;
import me.combimagnetron.lagoon.operation.Operation;
import net.kyori.adventure.text.Component;

import java.util.function.Consumer;

public interface CanvasWindow {

    Operation<Void> onCursorMove(Cursor<?> cursor);

    Operation<Void> resize(Pos2D newSize);

    Holder<Element> elements();

    Pos2D position();

    Pos2D size();

    default boolean hitsCursor(Cursor cursor) {
        Pos2D cursorPos = cursor.position();
        return cursorPos.x() >= position().x() && cursorPos.y() >= position().y() && cursorPos.x() <= position().x() + size().x() && cursorPos.y() <= position().y() + size().y();
    }

    Operation<Component> draw();

    Operation<Void> add(Element element);

    void setOnCursorMove(Consumer<Cursor<?>> consumer);






}
