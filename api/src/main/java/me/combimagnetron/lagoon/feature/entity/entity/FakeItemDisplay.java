package me.combimagnetron.lagoon.feature.entity.entity;

import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.operation.Operation;

public interface FakeItemDisplay extends FakeEntity {

    Operation<Boolean> scale(Point point);

    Operation<Void> move(Point relativeMovement, int lerp);

    Operation<Void> move(Point relativeMovement);

}

