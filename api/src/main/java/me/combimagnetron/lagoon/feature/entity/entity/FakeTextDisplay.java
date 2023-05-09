package me.combimagnetron.lagoon.feature.entity.entity;

import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.operation.Operation;
import net.kyori.adventure.text.Component;

public interface FakeTextDisplay extends FakeEntity {

    Operation<Void> move(Point relativeMovement, int lerp);

    Operation<Void> move(Point relativeMovement);

    Operation<Void> text(Component component);

}

