package me.combimagnetron.comet.ai.math;

public record Equation(Function function) {

    public interface Function {
        double evaluate(double x);

    }

    public static Equation equation(Function function) {
        return new Equation(function);
    }

    public static double sin(double x) {
        return Math.sin(x);
    }

    public static double cos(double x) {
        return Math.cos(x);
    }

    public static double tan(double x) {
        return Math.tan(x);
    }

    public static double power(double x, double y) {
        return Math.pow(x, y);
    }

    public static double log(double x) {
        return Math.log(x);
    }

    public static double exp(double x) {
        return Math.exp(x);
    }

    public static double sqrt(double x) {
        return Math.sqrt(x);
    }

    public static double abs(double x) {
        return Math.abs(x);
    }

    public static double max(double x, double y) {
        return Math.max(x, y);
    }

    public static double min(double x, double y) {
        return Math.min(x, y);
    }

    static double floor(double x) {
        return Math.floor(x);
    }

    public static double ceil(double x) {
        return Math.ceil(x);
    }
    public static double round(double x) {
        return Math.round(x);
    }

    public static double pi() {
        return Math.PI;
    }

    public static double e() {
        return Math.E;
    }

    public static double rad(double deg) {
        return Math.toRadians(deg);
    }

    public static double deg(double rad) {
        return Math.toDegrees(rad);
    }

    public static double signum(double x) {
        return Math.signum(x);
    }

    public static double clamp(double x, double min, double max) {
        return Math.min(Math.max(x, min), max);
    }

    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

}
