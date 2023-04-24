package me.combimagnetron.lagoon.menu.canvas.metadata;

import me.combimagnetron.lagoon.menu.Pos2D;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.operation.ExecutableOperation;

public abstract class Resizable {
    private Pos2D size;

    public Resizable(Pos2D size) {
        this.size = size;
    }

    public Operation<Void> resize(Pos2D size) {
        return ExecutableOperation.of(unused -> {
            this.size = size;
            return null;
        }, size);
    }

    public Pos2D size() {
        return size;
    }

}
