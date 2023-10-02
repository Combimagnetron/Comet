package me.combimagnetron.comet.internal.entity.impl;

import me.combimagnetron.comet.internal.entity.metadata.Metadata;
import me.combimagnetron.comet.internal.entity.metadata.type.Boolean;
import me.combimagnetron.comet.internal.entity.metadata.type.Float;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3;
import me.combimagnetron.comet.internal.entity.Entity;

public class Interaction extends Entity.AbstractEntity {
    private float width;
    private float height;
    private boolean responsive;

    @Override
    public Vector3 position() {
        return null;
    }

    @Override
    public Data data() {
        return null;
    }

    @Override
    public Type type() {
        return null;
    }

    @Override
    public Metadata extend() {
        return Metadata.of(
                Float.of(width),
                Float.of(height),
                Boolean.of(responsive)
        );
    }
}
