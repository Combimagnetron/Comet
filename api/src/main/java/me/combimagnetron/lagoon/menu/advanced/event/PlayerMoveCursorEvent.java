package me.combimagnetron.lagoon.menu.advanced.event;

import me.combimagnetron.lagoon.event.Event;
import me.combimagnetron.lagoon.menu.Pos2D;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.operation.ExecutableOperation;
import me.combimagnetron.lagoon.player.GlobalPlayer;

public class PlayerMoveCursorEvent implements Event {
    private final GlobalPlayer<?> globalPlayer;
    private final Pos2D oldPos;
    private Pos2D newPos;
    private Pos2D deltaPos;

    public PlayerMoveCursorEvent(GlobalPlayer<?> globalPlayer, Pos2D oldPos, Pos2D newPos) {
        this.globalPlayer = globalPlayer;
        this.oldPos = oldPos;
        this.newPos = newPos;
        this.deltaPos = newPos.sub(oldPos);
    }

    public GlobalPlayer<?> globalPlayer() {
        return globalPlayer;
    }

    public Pos2D oldPos() {
        return oldPos;
    }

    public Pos2D newPos() {
        return newPos;
    }

    public Pos2D deltaPos() {
        return deltaPos;
    }

    public Operation<Pos2D> newPos(Pos2D newPos) {
        return ExecutableOperation.of(unused -> {
            this.newPos = newPos;
            this.deltaPos = newPos.sub(oldPos);
            return null;
        }, newPos);
    };

    @Override
    public Class<? extends Event> eventType() {
        return this.getClass();
    }
}
