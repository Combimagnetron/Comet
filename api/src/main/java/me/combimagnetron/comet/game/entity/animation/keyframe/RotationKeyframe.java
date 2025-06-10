package me.combimagnetron.comet.game.entity.animation.keyframe;

import me.combimagnetron.comet.game.entity.animation.InterpolationType;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;

public class RotationKeyframe extends Keyframe {
    private final Vector3d rotation;

    protected RotationKeyframe(InterpolationType interpolationType, Vector3d rotation) {
        super(interpolationType);
        this.rotation = rotation;
    }

    public Vector3d rotation() {
        return rotation;
    }

}
