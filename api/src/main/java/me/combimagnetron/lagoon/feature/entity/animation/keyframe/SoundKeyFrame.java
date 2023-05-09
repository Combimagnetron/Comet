package me.combimagnetron.lagoon.feature.entity.animation.keyframe;

import me.combimagnetron.lagoon.animation.effect.SoundEffect;
import org.bukkit.Sound;

public final class SoundKeyFrame extends KeyFrame {
    private final Sound sound;

    public SoundKeyFrame(final String interpolationStyle, final String sound) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0,  null);
        this.sound = Sound.valueOf(sound.toUpperCase());
    }

    public Sound sound() {
        return sound;
    }

    public SoundEffect asEffect() {
        return null;
    }

}
