package me.combimagnetron.comet.event;

import me.combimagnetron.comet.concurrency.LifeCycle;
import me.combimagnetron.comet.concurrency.Scheduler;
import me.combimagnetron.comet.util.Duration;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public sealed interface Dispatcher<T extends Event> permits Dispatcher.SimpleDispatcher {
    SimpleDispatcher<? extends Event> SIMPLE = new SimpleDispatcher<>();

    static <T extends Event> Dispatcher<T> dispatcher() {
        return (SimpleDispatcher<T>) SIMPLE;
    }

    void postCancellable(Class<T> type, T event);

    LifeCycle postAsync(Class<T> type, T event);

    void post(Class<T> type, T event);

    void post(T event);

    EventSubscriptionManager<T> manager();

    final class SimpleDispatcher<T extends Event> implements Dispatcher<T> {
        private final EventSubscriptionManager<T> subscriptionManager = new EventSubscriptionManager.Impl<>();

        @Override
        public void postCancellable(Class<T> type, T event) {

        }

        @Override
        public LifeCycle postAsync(Class<T> type, T event) {
            return Scheduler.run(() -> post(type, event), Duration.of(0, TimeUnit.SECONDS));
        }

        @Override
        public void post(Class<T> type, T event) {
            subscriptionManager.subscriptionMap().values().stream().filter(e -> e.getEventClass() == type).forEach(e -> e.handler().accept(event));
        }

        @Override
        public void post(T event) {
            post((Class<T>) event.getClass(), event);
        }

        @Override
        public EventSubscriptionManager<T> manager() {
            return subscriptionManager;
        }

    }

}
