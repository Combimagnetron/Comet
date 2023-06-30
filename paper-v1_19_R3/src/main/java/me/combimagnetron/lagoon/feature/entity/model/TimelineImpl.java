package me.combimagnetron.lagoon.feature.entity.model;

import me.combimagnetron.lagoon.feature.entity.model.ModeledEntity;
import me.combimagnetron.lagoon.feature.entity.model.Timeline;
import me.combimagnetron.lagoon.feature.entity.animation.Animation;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;

import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.operation.Operations;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

public class TimelineImpl implements Timeline {
    private final ModeledEntity modeledEntity;
    private Animation currentlyPlaying = null;

    public TimelineImpl(ModeledEntity modeledEntity) {
        this.modeledEntity = modeledEntity;
    }

    @Override
    public Operation<Operations.Routine> play(Animation animation) {
        return Operation.executable(() -> {
            this.currentlyPlaying = animation;
            return Operations.asyncRepeating(animation.tick(), 0L, 100L, TimeUnit.MILLISECONDS);
        });
    }

    @Override
    public Operation<Operations.Routine> play(String animation) {
        return play(modeledEntity.template().animations().get(animation));
    }

    @Override
    public Operation<Operations.Routine> playLooped(Animation animation) {
        return Operation.executable(() -> {
            return null;
        });
    }

    @Override
    public Operation<Point> bonePosAtTime(Bone bone, long time) {
        return Operation.executable(() -> {
            if (currentlyPlaying.currentTick() + time > currentlyPlaying.length()) return null;
            return currentlyPlaying.posAt(bone.uuid().toString(), time);
        });
    }

    @Nullable
    protected Animation currentlyPlaying() {
        return currentlyPlaying;
    }
}
