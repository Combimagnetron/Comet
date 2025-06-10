package me.combimagnetron.comet.image;

import me.combimagnetron.comet.game.menu.Pos2D;

public record Pixel(int x, int y, Color color) {

    public static Pixel of(int x, int y, Color color) {
        return new Pixel(x, y, color);
    }

    public static Pixel of(int x, int y, int rgb) {
        return new Pixel(x, y, Color.of(rgb));
    }

    public static Pixel of(Pos2D pos, Color color) {
        return new Pixel(pos.xi(), pos.yi(), color);
    }

}
