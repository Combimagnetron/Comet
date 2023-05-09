package me.combimagnetron.lagoon.feature.entity.animation.keyframe;


import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;

public class ScaleKeyFrame extends KeyFrame {
    private final double xScale, yScale, zScale;

    public ScaleKeyFrame(String interpolationStyle, Point size, Bone bone) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0, bone);
        this.xScale = size.x();
        this.yScale = size.y();
        this.zScale = size.z();
    }

    public Point scaleAxis() {
        return Point.of(xScale, yScale, zScale);
    }

}
