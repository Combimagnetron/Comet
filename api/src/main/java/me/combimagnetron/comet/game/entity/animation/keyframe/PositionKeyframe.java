package me.combimagnetron.comet.game.entity.animation.keyframe;

import me.combimagnetron.comet.game.entity.animation.InterpolationType;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;

public class PositionKeyframe extends Keyframe {
    private final Vector3d position;

    protected PositionKeyframe(InterpolationType interpolationType, Vector3d position) {
        super(interpolationType);
        this.position = position;
    }

    public Vector3d position() {
        return position;
    }

}
