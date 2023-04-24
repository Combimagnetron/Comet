package me.combimagnetron.lagoon.menu.advanced.render;

public record AspectRatio(int x, int y) {
    public static AspectRatio of(int x, int y) {
        return new AspectRatio(x, y);
    }

}
