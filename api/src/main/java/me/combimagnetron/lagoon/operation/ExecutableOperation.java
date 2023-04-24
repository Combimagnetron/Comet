package me.combimagnetron.lagoon.operation;

import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public final class ExecutableOperation<T> implements Operation<T> {
    private final Function<T, ?> executableCode;
    private final Object value;
    private Consumer<T> onCompletion = (t -> {});
    private Object returnValue;
    private OperationState state = OperationState.IDLE;

    private ExecutableOperation(Function<T, ?> executableCode, Object value) {
        this.executableCode = executableCode;
        this.value = value;
    }

    @Override
    public void onCompletion(Consumer<T> consumer) {
        this.onCompletion = consumer;
    }

    @Override
    public T complete() {
        return null;
    }

    @Override
    public void fail() {

    }


    @Override
    public OperationState state() {
        return state;
    }



    public Object value() {
        return value;
    }


    public static <V> Operation<V> of(Function<V, V> consumer, Object value) {
        return new me.combimagnetron.lagoon.operation.ExecutableOperation<>(consumer, value);
    }

}
