package me.combimagnetron.lagoon.feature.entity.animation.keyframe;

import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;

public abstract class DimensionalKeyFrame extends KeyFrame {
    protected DimensionalKeyFrame(InterpolationStyle style, Bone bone) {
        super(style, 0, 0, bone);
    }
}
