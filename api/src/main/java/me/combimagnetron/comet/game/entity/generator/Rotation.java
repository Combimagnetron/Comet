package me.combimagnetron.comet.game.entity.generator;

import java.util.Objects;

public final class Rotation {
    private final float angle = 6f;
    private final String axis = null;
    private final double[] origin;

    public Rotation(float angle, String axis, double[] origin) {
        this.origin = origin;
    }

    public Rotation(Object angle, double[] origin) {
        float iAngle;
        String iAxis;
        this.origin = new double[3];
        this.origin[0] = origin[0] / 2.5 + 8;
        this.origin[1] = origin[1] / 2.5;
        this.origin[2] = origin[2] / 2.5 + 8;
    }

    private static float angle(double rads) {
        return Math.round((float) Math.toDegrees(rads) * 10f) / 10f;
    }

    public float angle() {
        return angle;
    }

    public String axis() {
        return axis;
    }

    public double[] origin() {
        return origin;
    }

    @java.lang.Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Rotation) obj;
        return Float.floatToIntBits(this.angle) == Float.floatToIntBits(that.angle) &&
                Objects.equals(this.axis, that.axis) &&
                Objects.equals(this.origin, that.origin);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(angle, axis, origin);
    }

    @java.lang.Override
    public String toString() {
        return "Rotation[" +
                "angle=" + angle + ", " +
                "axis=" + axis + ", " +
                "origin=" + origin + ']';
    }


}
