package me.combimagnetron.comet.ai.response;

public record ResponseCurve(double slope, double base, double x, double y) {
    private final static ResponseCurve STANDARD = new ResponseCurve(0, 1.004, 0, 0);

    public double adjust(double variable, double weight) {
        if (weight > 100 || weight < 0) {
            throw new IllegalArgumentException("Weight must be between 0 and 100");
        }
        return variable * (y + slope * Math.pow(base, (weight - x)));
    }

    public static ResponseCurve standard() {
        return STANDARD;
    }

}
