package me.combimagnetron.comet.event;

import me.combimagnetron.comet.operation.Operation;

import java.util.Set;
import java.util.function.Consumer;

public interface EventBus {

    /*static <V extends Event> Operation<EventSubscription<V>> subscribe(Class<V> clazz, Consumer<? super V> consumer) {
        return Operation.executable(() -> new EventSubscription.Impl<>(clazz, consumer));
    }*/

    static <T extends Event> EventSubscription<T> subscribe(Class<T> clazz, EventFilter<T, ?> filter, Consumer<? super T> consumer) {
        return new EventSubscription.FilteredImpl<>(clazz, filter, consumer);
    }

    static <T extends Event> Set<EventSubscription<T>> subscriptions(Class<T> clazz) {
        return null;
    }


}
