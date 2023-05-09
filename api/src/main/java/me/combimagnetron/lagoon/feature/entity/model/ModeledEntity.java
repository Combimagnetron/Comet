package me.combimagnetron.lagoon.feature.entity.model;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.entity.animation.Animation;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;
import me.combimagnetron.lagoon.operation.Operation;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ModeledEntity {

    Identifier identifier();

    me.combimagnetron.lagoon.feature.entity.model.Timeline timeline();

    Collection<Bone> bones();

    Bone bone(String name);

    Animation fallback();

    me.combimagnetron.lagoon.feature.entity.model.ModelTemplate template();

    Operation<Void> teleport(Point point);

    Operation<Void> rotate(String name, EulerAngle angle);

    @Nullable
    Operation<Void> mountEntity(Entity entity);

    @Nullable
    Operation<Void> dismountEntity(Entity entity);

    Operation<Void> headRotation(EulerAngle angle);

    Operation<Point> position();

    Operation<Void> tick();

}
