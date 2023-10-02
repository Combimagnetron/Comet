package me.combimagnetron.comet.feature.entity.model.bone;

import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

import java.util.UUID;

public class MountBone extends Bone {

    public MountBone(String name, UUID uuid, Point origin, EulerAngle rotation) {
        super(name, uuid, origin, rotation, false, true, null);
    }

    public Operation<Void> mount(Entity entity) {
        return Operation.simple(() -> fakeArmorStand().bukkitEntity().addPassenger(entity));
    }

    public Operation<Void> dismount(Entity entity) {
        return Operation.simple(() -> fakeArmorStand().bukkitEntity().removePassenger(entity));
    }

}
