package me.combimagnetron.lagoon.menu.canvas;

import me.combimagnetron.lagoon.menu.Pos2D;
import me.combimagnetron.lagoon.util.Pair;

public class CanvasSize {
    private final Pos2D leftTop, leftBottom, rightTop, rightBottom;

    public CanvasSize(Pos2D leftTop, Pos2D leftBottom, Pos2D rightTop, Pos2D rightBottom) {
        this.leftTop = leftTop;
        this.leftBottom = leftBottom;
        this.rightTop = rightTop;
        this.rightBottom = rightBottom;
    }

    public Pair<Integer, Integer> calculateAspectRatio() {
        double width = ((leftTop.x() - rightTop.x()) * -1);
        double height = ((leftTop.y() - leftBottom.y()) * -1);
        return new Pair<>(0, 0);
    }

    public Pos2D leftTopCornerPos() {
        return leftTop;
    }

    public Pos2D leftBottomCornerPos() {
        return leftBottom;
    }

    public Pos2D rightTopCornerPos() {
        return rightTop;
    }

    public Pos2D rightBottomCornerPos() {
        return rightBottom;
    }





}
