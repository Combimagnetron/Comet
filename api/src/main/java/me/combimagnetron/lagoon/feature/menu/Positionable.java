package me.combimagnetron.lagoon.feature.menu;

import me.combimagnetron.lagoon.operation.Operation;
import org.jetbrains.annotations.NotNull;

public interface Positionable extends Comparable<Positionable> {

    Operation<Void> pos(Pos2D pos);

    Pos2D pos();

    @Override
    default int compareTo(@NotNull Positionable o) {
        return (int) (pos().x() - o.pos().x());
    }
}