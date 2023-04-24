package me.combimagnetron.lagoon.menu.advanced.application;

import me.combimagnetron.lagoon.menu.canvas.CanvasWindow;
import me.combimagnetron.lagoon.menu.Pos2D;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.data.Identifier;

public interface Application {

    Identifier identifier();

    CanvasWindow canvasWindow();

    default Operation<Void> resize(Pos2D newSize) {
        return canvasWindow().resize(newSize);
    }

    Operation<Void> open();


}
