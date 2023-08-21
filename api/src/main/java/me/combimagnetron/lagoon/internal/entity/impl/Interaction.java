package me.combimagnetron.lagoon.internal.entity.impl;

import me.combimagnetron.lagoon.internal.entity.Entity;
import me.combimagnetron.lagoon.internal.entity.metadata.Metadata;
import me.combimagnetron.lagoon.internal.entity.metadata.type.Boolean;
import me.combimagnetron.lagoon.internal.entity.metadata.type.Float;

public class Interaction extends Entity.AbstractEntity {
    private float width;
    private float height;
    private boolean responsive;

    @Override
    public Data data() {
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
