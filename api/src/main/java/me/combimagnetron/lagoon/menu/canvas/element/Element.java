package me.combimagnetron.lagoon.menu.canvas.element;

import me.combimagnetron.lagoon.menu.advanced.Cursor;
import me.combimagnetron.lagoon.menu.Pos2D;
import me.combimagnetron.lagoon.menu.canvas.metadata.Tickable;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.operation.Result;

public abstract class Element extends Tickable {
    private Pos2D position;
    private Pos2D size;

    public Element(Pos2D position, Pos2D size) {
        this.position = position;
        this.size = size;
    }

    public Pos2D position() {
        return position;
    }

    public boolean hitsCursor(Cursor cursor) {
        Pos2D cursorPos = cursor.position();
        return cursorPos.x() >= position.x() && cursorPos.y() >= position.y() && cursorPos.x() <= position.x() + size.x() && cursorPos.y() <= position.y() + size.y();
    }

    public abstract Operation<Result> handleCursorMove(Cursor cursor);


    public abstract Pos2D minSize();

    public abstract boolean colorable();

    public Operation<Void> position(Pos2D replacement) {
        return Operation.executable(() -> {
            this.position = replacement;
            return null;
        });
    };

    public abstract Operation<Void> draw();

    /**
     * Method to tick the element, empty/disabled by default. Override to use.
     *
     * @see Operation
     */
    public Operation<Boolean> tick() {
        return Operation.executable(() -> null);
    }

    public Operation<Void> resize(Pos2D size) {
        return Operation.simple(() -> {
            this.size = size;
        });
    }

}
