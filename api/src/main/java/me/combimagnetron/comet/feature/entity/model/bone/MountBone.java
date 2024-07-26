package me.combimagnetron.comet.feature.entity.model.bone;

import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;

import java.util.UUID;

public class MountBone extends Bone {

    public MountBone(String name, UUID uuid, Point origin, Object rotation) {
        super(name, uuid, origin, rotation, false, true, null);
    }



}
