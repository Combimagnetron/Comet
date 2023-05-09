package me.combimagnetron.lagoon.feature.entity.animation.keyframe;

import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;

public final class PositionKeyFrame extends KeyFrame {
    private final Point point;

    public PositionKeyFrame(final String interpolationStyle, final Point point, final Bone bone) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0L, -1, bone);
        this.point = point;
    }

    public Point point() {
        return point;
    }
}
