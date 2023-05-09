package me.combimagnetron.lagoon.feature.entity.math;

import org.jetbrains.annotations.UnknownNullability;
import org.joml.Vector3f;

public record Point(double x, double y, double z, double pitch, double yaw) {

    public static Point of(double x, double y, double z, double pitch, double yaw) {
        return new Point(x, y, z, pitch, yaw);
    }

    public static Point of(double x, double y, double z) {
        return new Point(x, y, z, 0, 0);
    }

    @UnknownNullability
    public static Point of(int[] ints) {
        if (ints.length != 3) return null;
        return new Point(ints[0], ints[1], ints[2], 0, 0);
    }

    public Vector3f vector3f() {
        return new Vector3f((float) x, (float) y, (float) z);
    }

    public Point add(double x, double y, double z) {
        return new Point(this.x + x, this.y + y, this.z + z, pitch, yaw);
    }

    public Point add(double x, double y, double z, double pitch, double yaw) {
        return new Point(this.x + x, this.y + y, this.z + z, this.pitch + pitch, this.yaw + yaw);
    }

    public Point sub(double x, double y, double z) {
        return new Point(this.x - x, this.y - y, this.z - z, pitch, yaw);
    }

    public Point sub(double x, double y, double z, double pitch, double yaw) {
        return new Point(this.x - x, this.y - y, this.z - z, this.pitch - pitch, this.yaw - yaw);
    }

    public Point mul(double x, double y, double z) {
        return new Point(this.x * x, this.y * y, this.z * z, pitch, yaw);
    }


    public Point mul(double fact) {
        return new Point(this.x * fact, this.y * fact, this.z * fact, this.pitch * fact, this.yaw * fact);
    }

    public Point div(double x, double y, double z) {
        return new Point(this.x / x, this.y / y, this.z / z, pitch, yaw);
    }

    public Point div(double x, double y, double z, double pitch, double yaw) {
        return new Point(this.x / x, this.y / y, this.z / z, this.pitch / pitch, this.yaw / yaw);
    }
}
