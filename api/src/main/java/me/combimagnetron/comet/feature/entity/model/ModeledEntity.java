package me.combimagnetron.comet.feature.entity.model;

import me.combimagnetron.comet.feature.entity.animation.Animation;
import me.combimagnetron.comet.feature.entity.model.bone.Bone;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ModeledEntity {

    Identifier identifier();

    Timeline timeline();

    Collection<Bone> bones();

    Bone bone(String name);

    Animation fallback();

    ModelTemplate template();

    Operation<Void> teleport(Point point);

    Operation<Void> rotate(String name, EulerAngle angle);

    @Nullable
    Operation<Void> mountEntity(Entity entity);

    @Nullable
    Operation<Void> dismountEntity(Entity entity);

    Operation<Void> headRotation(EulerAngle angle);

    Operation<Point> position();

    Operation<Boolean> tick();

}
