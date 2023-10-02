package me.combimagnetron.comet.feature.entity.animation.keyframe;


import me.combimagnetron.comet.feature.entity.animation.effect.ScriptEffect;

public final class ScriptKeyFrame extends KeyFrame {
    private final String scriptType;
    private final String scriptArgs;

    public ScriptKeyFrame(final String interpolationStyle, final String script) {
        super(InterpolationStyle.valueOf(interpolationStyle.toUpperCase()), 0, 0, null);
        String[] split = script.split(":");
        this.scriptType = split[0];
        this.scriptArgs = split[1];
    }

    public String scriptType() {
        return scriptType;
    }

    public String scriptArgs() {
        return scriptArgs;
    }

    public ScriptEffect asEffect() {
        return null;
    }


}
