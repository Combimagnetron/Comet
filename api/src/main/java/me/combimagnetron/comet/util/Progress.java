package me.combimagnetron.comet.util;

public record Progress(double current, double total, boolean loop) {

    public static Progress of(double current, double total, boolean loop) {
        return new Progress(current, total, loop);
    }

}
