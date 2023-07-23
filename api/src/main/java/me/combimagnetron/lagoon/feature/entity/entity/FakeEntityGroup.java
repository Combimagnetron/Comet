package me.combimagnetron.lagoon.feature.entity.entity;

import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.World;
import org.bukkit.util.EulerAngle;

import java.util.Collection;

public interface FakeEntityGroup {

    Operation<Void> show(User<?> player);

    Operation<Void> teleport(Point point, World world);

    Operation<Void> rotate(EulerAngle angle);

    Collection<FakeEntity> members();

    Operation<Void> update(User<?> player);
}
