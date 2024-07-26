package me.combimagnetron.comet.feature.entity.entity;


import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;
import me.combimagnetron.comet.user.User;


import java.util.Collection;

public interface FakeEntity {

    Operation<Boolean> show(Collection<User<?>> players);

    Operation<Boolean> hide(Collection<User<?>> players);

    Operation<Void> headItem(Object stack);

    Operation<Void> teleport(Point point);

    Operation<Void> rotateHead(Object angle);

    Operation<Collection<User<?>>> viewers();

    Object bukkitEntity();

}
