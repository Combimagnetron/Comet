package me.combimagnetron.comet.game.entity.animation;

import me.combimagnetron.comet.concurrency.LifeCycle;
import me.combimagnetron.comet.concurrency.Scheduler;
import me.combimagnetron.comet.util.Duration;

import java.util.concurrent.TimeUnit;

public class Timeline {

    public LifeCycle play(Animation animation) {
        if (animation.loops()) {
            return Scheduler.loop(animation::tick, Duration.of(20L, TimeUnit.MILLISECONDS));
        }
        return Scheduler.run(animation::tick, Duration.of(0L, TimeUnit.MILLISECONDS));
    }

    public LifeCycle play(String animation) {
        return null;
    }


}
