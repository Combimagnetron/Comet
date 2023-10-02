package me.combimagnetron.comet.feature.entity.entity;

import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;

public interface FakeItemDisplay extends FakeEntity {

    Operation<Boolean> scale(Point point);

    Operation<Void> move(Point relativeMovement, int lerp);

    Operation<Void> move(Point relativeMovement);

}

