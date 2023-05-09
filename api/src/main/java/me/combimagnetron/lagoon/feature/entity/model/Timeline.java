package me.combimagnetron.lagoon.feature.entity.model;

import me.combimagnetron.lagoon.feature.entity.Duration;
import me.combimagnetron.lagoon.feature.entity.animation.Animation;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;
import me.combimagnetron.lagoon.operation.Operation;

import me.combimagnetron.lagoon.operation.Operations;
import org.jetbrains.annotations.Nullable;

public interface Timeline {

    Operation<Operations.Routine> play(Animation animation);

    Operation<Operations.Routine> play(String animation);

    Operation<Operations.Routine> playLooped(Animation animation);

    Operation<Point> bonePosAtTime(Bone bone, long time);

    default Operation<Operations.Routine> queue(Animation animation, Duration delay) {
        return Operation.executable(() -> delay.run(play(animation)));
    }

    interface LifeCycle {

        Operation<Void> stop();

        Operation<Void> pause();

        @Nullable Operation<Void> resume();

    }

}
