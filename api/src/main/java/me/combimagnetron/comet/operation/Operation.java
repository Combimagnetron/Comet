package me.combimagnetron.comet.operation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface Operation<V> {

    Set<Operation<?>> chained = new LinkedHashSet<>();

    void onCompletion(Consumer<V> consumer);

    V complete();

    default Collection<Operation<?>> chained() {
        return Collections.unmodifiableSet(chained);
    }

    default Operation<V> chain(Operation<?> operation) {
        chained.add(operation);
        return this;
    }

    default V async() {
        return Operations.async(this);
    }

    static Operation<Void> simple(SimpleOperationConsumer consumer) {
        return new SimpleOperation(consumer);
    }

    static <V> Operation<V> executable(SimpleReturningConsumer<V> executableCode) {
        return new ExecutableOperation<>(executableCode);
    }

    static <V> Operation<V> await(SimpleReturningConsumer<V> executableCode, SimpleReturningConsumer<Boolean> holdCondition) {
        return new AwaitingOperation<>(executableCode, holdCondition);
    }

    final class SimpleOperation implements Operation<Void> {
        private final SimpleOperationConsumer simpleOperationConsumer;
        private Consumer<Void> consumer = unused -> {};

        public SimpleOperation(SimpleOperationConsumer consumer) {
            this.simpleOperationConsumer = consumer;
        }

        @Override
        public void onCompletion(Consumer<Void> consumer) {
            this.consumer = consumer;
        }

        @Override
        public Void complete() {
            simpleOperationConsumer.run();
            consumer.accept(null);
            return null;
        }

    }

    @FunctionalInterface
    interface SimpleOperationConsumer {
        void run();
    }

    final class AwaitingOperation<T> extends ExecutableOperation<T> {
        private final Checker checker;
        AwaitingOperation(SimpleReturningConsumer<T> executableCode, SimpleReturningConsumer<Boolean> holdCondition) {
            super(executableCode);
            this.checker = new Checker(executableCode, holdCondition);
        }
    }

    class ExecutableOperation<T> implements Operation<T> {
        private final SimpleReturningConsumer<T> executableCode;
        private Consumer<T> consumer = t -> {};

        ExecutableOperation(SimpleReturningConsumer<T> executableCode) {
            this.executableCode = executableCode;
        }

        @Override
        public void onCompletion(Consumer<T> consumer) {
            this.consumer = consumer;
        }

        @Override
        public T complete() {
            T t = executableCode.run();
            consumer.accept(t);
            return t;
        }

    }

    @FunctionalInterface
    interface SimpleReturningConsumer<T> {
        T run();
    }

    final class Checker {
        private final Operation<Boolean> checkerOperation;
        private Operations.Routine routine;

        Checker(SimpleReturningConsumer<?> simpleReturningConsumer, SimpleReturningConsumer<Boolean> condition) {
            this.checkerOperation = Operation.executable(() -> {
                if (condition.run()) {
                    simpleReturningConsumer.run();
                    Operations.async(routine.stop());
                    return true;
                }
                return false;
            });
            this.routine = Operations.asyncRepeating(checkerOperation, 0L, 3L, TimeUnit.MILLISECONDS);
        }
    }

}
