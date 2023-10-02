package me.combimagnetron.comet.feature.entity.model;

import me.combimagnetron.comet.feature.entity.animation.Animation;
import me.combimagnetron.comet.feature.entity.model.bone.Bone;
import me.combimagnetron.comet.data.Identifier;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class ModelTemplate {
    private final TreeMap<UUID, Animation> animations = new TreeMap<>();
    private final TreeMap<UUID, Bone> bones = new TreeMap<>();
    private final Identifier identifier;

    public ModelTemplate(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier identifier() {
        return identifier;
    }

    public Map<UUID, Animation> animations() {
        return animations;
    }

    public Map<UUID, Bone> bones() {
        return bones;
    }


}
