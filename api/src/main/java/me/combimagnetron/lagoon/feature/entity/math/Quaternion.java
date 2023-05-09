package me.combimagnetron.lagoon.feature.entity.math;

import org.joml.Quaternionf;

// Credit: https://github.com/SeedNexus/WorldSeedEntityEngine/blob/master/src/main/java/net/worldseed/multipart/Quaternion.java. math is hard, so I ain't gon do it myself lol
public class Quaternion {
    private final double x;
    private final double y;
    private final double z;

    private final double w;

    public Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternionf toF() {
        return new Quaternionf(this.x, this.y, this.z, this.w);
    }

    public static Quaternion quaternion(Point point) {
        return new Quaternion(point);
    }

    public Quaternion(Point p) {
        p = p.mul(57.29577951308232F);

        double cy = Math.cos(p.z() * 0.5);
        double sy = Math.sin(p.z() * 0.5);
        double cp = Math.cos(p.y() * 0.5);
        double sp = Math.sin(p.y() * 0.5);
        double cr = Math.cos(p.x() * 0.5);
        double sr = Math.sin(p.x() * 0.5);

        w = cr * cp * cy + sr * sp * sy;
        x = sr * cp * cy - cr * sp * sy;
        y = cr * sp * cy + sr * cp * sy;
        z = cr * cp * sy - sr * sp * cy;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public double w() {
        return w;
    }

    public Point toEuler() {
        double t0 = (x+z)*(x-z);
        double t1 = (w+y)*(w-y);
        double xx = 0.5*(t0+t1);
        double xy = x*y+w*z;
        double xz = w*y-x*z;
        double t  = xx*xx+xy*xy;
        double yz = 2.0*(y*z+w*x);

        double vx, vy, vz;

        vz = (float)Math.atan2(xy, xx);
        vy = (float)Math.atan(xz/Math.sqrt(t));

        if (t != 0)
            vx = (float)Math.atan2(yz, t1-t0);
        else
            vx = (float)(2.0*Math.atan2(x,w) - Math.signum(xz)*vz);

        return new Point(vx, vy, vz, 0, 0).mul(0.017453292519943295F);
    }

    public Quaternion multiply(Quaternion q) {
        double w = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
        double x = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
        double y = this.w * q.y - this.x * q.z + this.y * q.w + this.z * q.x;
        double z = this.w * q.z + this.x * q.y - this.y * q.x + this.z * q.w;

        return new Quaternion(
                x,y,z,w
        );
    }

    Point threeAxisRot(double r11, double r12, double r21, double r31, double r32){
        double x = Math.atan2(r31, r32);
        double y = Math.asin(r21);
        double z = Math.atan2(r11, r12);
        return new Point(x,z,y,0,0);
    }

    public Point toEulerYZX() {
        Quaternion q = this;
        return threeAxisRot( -2*(q.x*q.z - q.w*q.y),
                q.w*q.w + q.x*q.x - q.y*q.y - q.z*q.z,
                2*(q.x*q.y + q.w*q.z),
                -2*(q.y*q.z - q.w*q.x),
                q.w*q.w - q.x*q.x + q.y*q.y - q.z*q.z).mul(0.017453292519943295F);
    }
}
