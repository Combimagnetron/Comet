package me.combimagnetron.lagoon.menu.canvas;

import me.combimagnetron.lagoon.menu.Pos2D;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.util.Pair;

import java.util.Collection;

public interface Canvas {

    /**
     * Main resize method for the whole canvas (including all the underlying elements and windows).
     * @param newSize, the new size. (X first, Y second).
     * @return Simple operation which runs the resize process when ran.
     */
    Operation<Void> resize(Pos2D newSize);


    /**
     * @return All the canvas windows.
     */
    Collection<CanvasWindow> windows();

    /**
     * The saved screen size of the player, it saves the in-game location of every corner.
     * It is used to determine window placement and max size.
     */
    class CanvasSize {
        private final Pos2D leftTop, leftBottom, rightTop, rightBottom;

        /**
         * Only constructor for the CanvasSize class.
         * @param leftTop Left top corner position.
         * @param leftBottom Left bottom corner position.
         * @param rightTop Right top corner position.
         * @param rightBottom Right bottom corner position.
         */
        public CanvasSize(Pos2D leftTop, Pos2D leftBottom, Pos2D rightTop, Pos2D rightBottom) {
            this.leftTop = leftTop;
            this.leftBottom = leftBottom;
            this.rightTop = rightTop;
            this.rightBottom = rightBottom;
        }


        /**
         * Pair with the width and height of the user's screen.
         * It is calculated purely with the given measurements, so it is not relative to the user's in-game GUI-scale.
         * @return A pair with the width and height of the screen.
         */
        public Pair<Integer, Integer> widthAndHeight() {
            double width = ((leftTop.x() - rightTop.x()) * -1);
            double height = ((leftTop.y() - leftBottom.y()) * -1);
            return new Pair<>(0, 0);
        }

        /**
         * @return Pos2D with X and Y of the left top corner.
         */
        public Pos2D leftTopCornerPos() {
            return leftTop;
        }

        /**
         * @return Pos2D with X and Y of the left bottom corner.
         */
        public Pos2D leftBottomCornerPos() {
            return leftBottom;
        }

        /**
         * @return Pos2D with X and Y of the right top corner.
         */
        public Pos2D rightTopCornerPos() {
            return rightTop;
        }

        /**
         * @return Pos2D with X and Y of the right bottom corner.
         */
        public Pos2D rightBottomCornerPos() {
            return rightBottom;
        }

    }
}
