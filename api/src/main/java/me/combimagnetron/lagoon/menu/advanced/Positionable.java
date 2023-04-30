package me.combimagnetron.lagoon.menu.advanced;

import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.util.Pos2D;
import org.jetbrains.annotations.NotNull;

public interface Positionable extends Comparable<Positionable> {

    Operation<Void> pos(Pos2D pos);

    Pos2D pos();

    @Override
    default int compareTo(@NotNull Positionable o) {
        return (int) (pos().x() - o.pos().x());
    }
}
