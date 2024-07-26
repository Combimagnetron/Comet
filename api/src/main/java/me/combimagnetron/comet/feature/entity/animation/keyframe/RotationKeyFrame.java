package me.combimagnetron.comet.feature.entity.animation.keyframe;

import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.feature.entity.model.bone.Bone;

public final class RotationKeyFrame extends KeyFrame{
    private final Object angle;

    public RotationKeyFrame(final String interpolationStyle, final Object angle, final Bone bone) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0,  bone);
        this.angle = angle;
    }

    public RotationKeyFrame(final String interpolationStyle, final Point angle, final Bone bone) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0,  bone);
        this.angle = null;
    }

    public Object eulerAngle() {
        return angle;
    }
}
