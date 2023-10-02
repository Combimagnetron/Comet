package me.combimagnetron.comet.feature.entity.entity;

import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;
import net.kyori.adventure.text.Component;

public interface FakeTextDisplay extends FakeEntity {

    Operation<Void> move(Point relativeMovement, int lerp);

    Operation<Void> move(Point relativeMovement);

    Operation<Void> text(Component component);

}

