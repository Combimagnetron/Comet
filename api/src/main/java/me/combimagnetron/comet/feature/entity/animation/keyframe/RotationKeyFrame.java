package me.combimagnetron.comet.feature.entity.animation.keyframe;

import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.feature.entity.model.bone.Bone;
import org.bukkit.util.EulerAngle;

public final class RotationKeyFrame extends KeyFrame{
    private final EulerAngle angle;

    public RotationKeyFrame(final String interpolationStyle, final EulerAngle angle, final Bone bone) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0,  bone);
        this.angle = angle;
    }

    public RotationKeyFrame(final String interpolationStyle, final Point angle, final Bone bone) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0,  bone);
        this.angle = new EulerAngle(angle.x(), angle.y(), angle.z());
    }

    public EulerAngle eulerAngle() {
        return angle;
    }
}
