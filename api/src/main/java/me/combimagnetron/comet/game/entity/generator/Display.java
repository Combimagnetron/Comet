package me.combimagnetron.comet.game.entity.generator;

public class Display {
    private final double[] translation;
    private final double[] scale;

    public Display() {
        this.translation = new double[]{0, 25.6, 0};
        this.scale = new double[]{4, 4, 4};
    }

    public double[] scale() {
        return scale;
    }

    public double[] translation() {
        return translation;
    }
}
