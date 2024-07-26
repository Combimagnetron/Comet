package me.combimagnetron.comet.feature.entity.animation.keyframe;


import me.combimagnetron.comet.feature.entity.animation.effect.ParticleEffect;

public final class ParticleKeyFrame extends KeyFrame {
    private final Object particle;

    public ParticleKeyFrame(final String interpolationStyle, final String particle) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0, null);
        this.particle = null;
    }


    public ParticleEffect asEffect() {
        return null;
    }

}
