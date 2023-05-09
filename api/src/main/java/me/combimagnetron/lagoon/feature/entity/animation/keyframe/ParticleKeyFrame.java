package me.combimagnetron.lagoon.feature.entity.animation.keyframe;


import me.combimagnetron.lagoon.feature.entity.animation.effect.ParticleEffect;
import org.bukkit.Particle;

public final class ParticleKeyFrame extends KeyFrame {
    private final Particle particle;

    public ParticleKeyFrame(final String interpolationStyle, final String particle) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0, null);
        this.particle = Particle.valueOf(particle.toUpperCase());
    }

    public Particle particle() {
        return particle;
    }

    public ParticleEffect asEffect() {
        return null;
    }

}
