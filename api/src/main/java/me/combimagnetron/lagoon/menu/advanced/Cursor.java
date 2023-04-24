package me.combimagnetron.lagoon.menu.advanced;

import me.combimagnetron.lagoon.menu.Pos2D;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.player.GlobalPlayer;

public class Cursor<E> {
    private final E armorStand;
    private final GlobalPlayer<?> owner;
    private Pos2D lastPosition = Pos2D.of(0, 0);
    private Pos2D position;
    private CursorState state = CursorState.DEFAULT;

    public Cursor(E armorStand ,Pos2D position, GlobalPlayer<?> owner) {
        this.armorStand = armorStand;
        this.position = position;
        this.owner = owner;
    }

    public void defaultState() {
        this.state = CursorState.DEFAULT;
    }

    public void clickState() {
        this.state = CursorState.CLICK;
    }

    public void moveState() {
        this.state = CursorState.MOVE;
    }

    public void resizeDiagonalState() {
        this.state = CursorState.RESIZE_DIAGONAL;
    }

    public void resizeHorizontal() {
        this.state = CursorState.RESIZE_HORIZONTAL;
    }

    public void resizeVertical() {
        this.state = CursorState.RESIZE_VERTICAL;
    }

    public Operation<Void> move(Pos2D newPos) {
        return Operation.simple(() -> {
            this.lastPosition = this.position;
            this.position = newPos;
        });
    }

    public Pos2D position() {
        return this.position;
    }

    public Pos2D positionDelta() {
        return this.position.sub(this.lastPosition);
    }

    public Pos2D lastPosition() {
        return this.lastPosition;
    }

    public GlobalPlayer<?> owner() {
        return owner;
    }

    public E armorStand() {
        return armorStand;
    }

    public enum CursorState {
        DEFAULT, CLICK, MOVE, RESIZE_DIAGONAL, RESIZE_HORIZONTAL, RESIZE_VERTICAL
    }
}
