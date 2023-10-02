package me.combimagnetron.comet.feature.entity.model.bone;


import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.feature.entity.model.Cube;
import org.bukkit.util.EulerAngle;

import java.util.UUID;

public class HeadBone extends Bone {
    public HeadBone(String name, UUID uuid, Point origin, EulerAngle rotation, boolean mirrorUV, boolean visibility, Cube[] cubeArray) {
        super(name, uuid, origin, rotation, mirrorUV, visibility, cubeArray);
    }
}
