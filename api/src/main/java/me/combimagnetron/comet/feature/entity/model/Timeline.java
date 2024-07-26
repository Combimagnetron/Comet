package me.combimagnetron.comet.feature.entity.model;

import me.combimagnetron.comet.feature.entity.Duration;
import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;

import me.combimagnetron.comet.operation.Operations;
import org.jetbrains.annotations.Nullable;

public interface Timeline {

    //Operation<Operations.Routine> play(Animation animation);

    Operation<Operations.Routine> play(String animation);

    //Operation<Operations.Routine> playLooped(Animation animation);

    //Operation<Point> bonePosAtTime(Bone bone, long time);

    /*default Operation<Operations.Routine> queue(Animation animation, Duration delay) {
        return Operation.executable(() -> delay.run(play(animation)));
    }*/

    interface LifeCycle {

        void stop();

        void pause();

        void resume();

    }

}
