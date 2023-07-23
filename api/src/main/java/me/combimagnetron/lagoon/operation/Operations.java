package me.combimagnetron.lagoon.operation;

import me.combimagnetron.lagoon.feature.entity.Duration;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unchecked")
public class Operations {
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(4);
    private static final String PLUGIN_NAME = "Lagoon";

    @UnknownNullability
    public static <T> T async(Operation<T> operation) {
        if (operation == null) return null;
        try {
            return EXECUTOR_SERVICE.submit(() -> {
                T t = operation.complete();
                operation.chained().forEach(Operations::async);
                return t;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            operation.fail();
            throw new RuntimeException(e);
        }
    }

    @UnknownNullability
    public static <T> T sync(Operation<T> operation) {
        if (operation == null) return null;
        AtomicReference<T> t = new AtomicReference<>();
        Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin(PLUGIN_NAME), () -> {
            t.set(operation.complete());
            //operation.chained().forEach(Operations::sync);
        });
        return t.get();
    }

    public static <T> Routine asyncRepeating(Operation<T> operation, long delay, long interval, TimeUnit unit) {
        return new Routine(EXECUTOR_SERVICE.scheduleAtFixedRate(() -> async(operation), delay, interval, unit));
    }

    public static <T> void asyncRepeating(Operation<T> operation, Duration duration) {
        try {
            EXECUTOR_SERVICE.scheduleAtFixedRate(operation::complete, 0L, duration.time(), duration.unit()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asyncDelayed(Operation<T> operation, Duration duration) {
        try {
            return EXECUTOR_SERVICE.schedule(() -> async(operation), duration.time(), duration.unit()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static class Routine  {
        private final ScheduledFuture<?> scheduledFuture;

        public Routine(ScheduledFuture<?> scheduledFuture) {
            this.scheduledFuture = scheduledFuture;
        }


        public Operation<Void> stop() {
            return Operation.simple(() -> scheduledFuture.cancel(true));
        }

        public Operation<Void> pause() {
            return null;
        }

        public @Nullable Operation<Void> resume() {
            return null;
        }

        @Nullable
        public <V> V get() {
            try {
                return (V) scheduledFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
