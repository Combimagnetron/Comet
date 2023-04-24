package me.combimagnetron.lagoon.menu.canvas.metadata;

import me.combimagnetron.lagoon.operation.Operation;

public abstract class Tickable {

    public abstract Operation<Boolean> tick();

}
