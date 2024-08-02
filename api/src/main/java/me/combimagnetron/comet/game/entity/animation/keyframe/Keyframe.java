package me.combimagnetron.comet.game.entity.animation.keyframe;

import me.combimagnetron.comet.game.entity.animation.InterpolationType;

public abstract class Keyframe {
    private final InterpolationType interpolationType;

    protected Keyframe(InterpolationType interpolationType) {
        this.interpolationType = interpolationType;
    }

    public InterpolationType interpolationType() {
        return interpolationType;
    }

}
