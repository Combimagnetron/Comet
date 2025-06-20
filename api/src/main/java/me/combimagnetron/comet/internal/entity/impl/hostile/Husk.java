package me.combimagnetron.comet.internal.entity.impl.hostile;

import me.combimagnetron.comet.internal.entity.Entity;
import me.combimagnetron.comet.internal.entity.metadata.Metadata;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;

public class Husk extends Entity.AbstractEntity {

    Husk(Vector3d position) {
        super(position);
    }

    public static Husk husk(Vector3d position) {
        return new Husk(position);
    }

    @Override
    public Data data() {
        return Data.of(0);
    }

    @Override
    public Type type() {
        return null;
    }

    @Override
    public Metadata extend() {
        return Metadata.of();
    }
}
