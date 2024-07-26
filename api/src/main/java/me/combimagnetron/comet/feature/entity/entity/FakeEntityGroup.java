package me.combimagnetron.comet.feature.entity.entity;

import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;
import me.combimagnetron.comet.user.User;

import java.util.Collection;

public interface FakeEntityGroup {

    Operation<Void> show(User<?> player);

    Operation<Void> teleport(Point point, Object world);

    Operation<Void> rotate(Object angle);

    Collection<FakeEntity> members();

    Operation<Void> update(User<?> player);
}
