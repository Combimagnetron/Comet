package me.combimagnetron.comet.feature.entity;

import me.combimagnetron.comet.operation.Operation;
import me.combimagnetron.comet.operation.Operations;

import java.util.concurrent.TimeUnit;

public record Duration(long time, TimeUnit unit) {

    public static Duration of(long time, TimeUnit unit) {
        return new Duration(time, unit);
    }

    public <T> T run(Operation<T> operation) {
        return Operations.asyncDelayed(operation, this);
    }

}
