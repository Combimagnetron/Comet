package me.combimagnetron.comet.feature.entity.animation.keyframe;

import me.combimagnetron.comet.feature.entity.animation.effect.SoundEffect;

public final class SoundKeyFrame extends KeyFrame {

    public SoundKeyFrame(final String interpolationStyle, final String sound) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0,  null);
    }


    public SoundEffect asEffect() {
        return null;
    }

}
