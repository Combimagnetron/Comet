package me.combimagnetron.comet.feature.entity.animation.keyframe;

import me.combimagnetron.comet.feature.entity.model.bone.Bone;

public abstract class KeyFrame {
    protected final Bone bone;
    private final long time;
    private final int color;
    private final InterpolationStyle style;

    protected KeyFrame(InterpolationStyle style, long time, int color, Bone bone) {
        this.style = style;
        this.bone = bone;
        this.time = time;
        this.color = color;
    }


    public enum InterpolationStyle {
        LINEAR, SMOOTH, STEP
    }

    public Bone bone() {
        return bone;
    }

    public long time() {
        return time;
    }

    public int color() {
        return color;
    }

    public InterpolationStyle style() {
        return style;
    }

}
