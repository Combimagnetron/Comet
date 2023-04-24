package me.combimagnetron.lagoon.menu.advanced.render;

import me.combimagnetron.lagoon.menu.advanced.application.Application;
import me.combimagnetron.lagoon.menu.canvas.Canvas;
import me.combimagnetron.lagoon.menu.canvas.CanvasWindow;
import me.combimagnetron.lagoon.operation.Operation;

/**
 * An interface that handles the rendering of all elements, canvases and canvas windows.
 */
public interface RenderSystem {

    /**
     * @see me.combimagnetron.lagoon.menu.canvas.Canvas.CanvasSize
     * @return The user's canvas/screen size.
     */
    Canvas.CanvasSize canvasSize();

    /**
     * @return User's main (and only) canvas.
     */
    Canvas canvas();

    /**
     * @param New canvasSize
     * @return Simple operation which changes the canvasSize value when called.
     */
    Operation<Void> canvasSize(Canvas.CanvasSize canvasSize);

    /**
     *
     * @param The application to open.
     * @return Operation with boolean which runs the opening process when called.
     */
    Operation<Boolean> open(Application application);

    /**
     * Shows a canvas window directly, without an application.
     * @param The canvas window to show.
     * @return Operation with boolean which runs the show process when called.
     */
    Operation<Boolean> show(CanvasWindow canvasWindow);
}
