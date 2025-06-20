package me.combimagnetron.comet.internal.entity.impl.display;

import me.combimagnetron.comet.internal.entity.Entity;
import me.combimagnetron.comet.internal.entity.metadata.Metadata;
import me.combimagnetron.comet.internal.entity.metadata.type.Float;
import me.combimagnetron.comet.internal.entity.metadata.type.Quaternion;
import me.combimagnetron.comet.internal.entity.metadata.type.VarInt;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Display extends Entity.AbstractEntity {
    protected int interpolationDelay = 0;
    protected int interpolationDuration = 0;
    protected Transformation transformation = new Transformation(Vector3d.vec3(0, 0, 0), Vector3d.vec3(1, 1, 1), Quaternion.of(0, 0, 0, 1), Quaternion.of(0, 0, 0, 1));
    protected Billboard billboard = Billboard.FIXED;
    protected int brightness = -1;
    protected float viewRange = 1;
    protected Shadow shadow = Shadow.shadow();
    protected int glowOverride = -1;
    protected float width = 0;
    protected float height = 0;

    public Display(Vector3d position, Consumer<Display> loaded) {
        super(position);
        loaded.accept(this);
    }

    @Override
    public Metadata extend() {
        return null;
    }

    @Override
    public Entity.Data data() {
        return Data.of(0);
    }

    @Override
    public Type type() {
        return null;
    }

    public record Transformation(Vector3d translation, Vector3d scale, Quaternion rotationLeft, Quaternion rotationRight) {

        public static Transformation transformation() {
            return of(Vector3d.vec3(0, 0, 0), Vector3d.vec3(1, 1, 1), Quaternion.of(0, 0, 0, 1), Quaternion.of(0, 0, 0, 1));
        }

        public static Transformation of(Vector3d translation, Vector3d scale, Quaternion rotationLeft, Quaternion rotationRight) {
            return new Transformation(translation, scale, rotationLeft, rotationRight);
        }

        public Transformation translation(Vector3d translation) {
            return of(translation, scale(), rotationLeft(), rotationRight());
        }

        public Transformation scale(Vector3d scale) {
            return of(translation(), scale, rotationLeft(), rotationRight());
        }

        public Transformation rotationLeft(Quaternion rotationLeft) {
            return of(translation(), scale(), rotationLeft, rotationRight());
        }

        public Transformation rotationRight(Quaternion rotationRight) {
            return of(translation(), scale(), rotationLeft(), rotationRight);
        }

    }

    public record Shadow(float radius, float strength) {

        public static Shadow of(float radius, float strength) {
            return new Shadow(radius, strength);
        }

        public static Shadow shadow() {
            return of(0, 1);
        }

    }

    public Metadata base() {
        return Metadata.of(
                VarInt.of(interpolationDelay()),
                VarInt.of(0),
                VarInt.of(0),
                transformation().translation(),
                transformation().scale(),
                transformation().rotationLeft(),
                transformation().rotationRight(),
                me.combimagnetron.comet.internal.entity.metadata.type.Byte.of(billboard().constraint()),
                VarInt.of(brightness()),
                me.combimagnetron.comet.internal.entity.metadata.type.Float.of(viewRange()),
                me.combimagnetron.comet.internal.entity.metadata.type.Float.of(shadow().radius()),
                me.combimagnetron.comet.internal.entity.metadata.type.Float.of(shadow().strength()),
                me.combimagnetron.comet.internal.entity.metadata.type.Float.of(width()),
                Float.of(height()),
                VarInt.of(glowOverride())
        );
    }

    public interface Billboard {
        Billboard FIXED = of(0);
        Billboard VERTICAL = of(1);
        Billboard HORIZONTAL = of(2);
        Billboard CENTER = of(3);

        byte constraint();

        static Billboard of(int constraint) {
            return new Impl((byte)constraint);
        }

        record Impl(byte constraint) implements Billboard {

        }

    }

    public int interpolationDelay() {
        return interpolationDelay;
    }

    public void interpolationDelay(int interpolationDelay) {
        this.interpolationDelay = interpolationDelay;
    }

    public int interpolationDuration() {
        return interpolationDuration;
    }

    public void interpolationDuration(int interpolationDuration) {
        this.interpolationDuration = interpolationDuration;
    }

    public Transformation transformation() {
        return transformation;
    }

    public void transformation(Transformation transformation) {
        //this.transformation = transformation;
    }

    public Billboard billboard() {
        return billboard;
    }

    public void billboard(Billboard billboard) {
        this.billboard = billboard;
    }

    public int brightness() {
        return brightness;
    }

    public void brightness(int brightness) {
        this.brightness = brightness;
    }

    public float viewRange() {
        return viewRange;
    }

    public void viewRange(float viewRange) {
        this.viewRange = viewRange;
    }

    public Shadow shadow() {
        return shadow;
    }

    public void shadow(Shadow shadow) {
        this.shadow = shadow;
    }

    public float width() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float height() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int glowOverride() {
        return glowOverride;
    }

    public void glowOverride(int glowOverride) {
        this.glowOverride = glowOverride;
    }

}
