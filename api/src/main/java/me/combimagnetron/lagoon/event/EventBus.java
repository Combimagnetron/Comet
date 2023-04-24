package me.combimagnetron.lagoon.event;

import me.combimagnetron.lagoon.operation.Operation;

import java.util.Set;
import java.util.function.Consumer;

public interface EventBus {

    <T extends Event> Operation<EventSubscription<T>> subscribe(Class<T> clazz, Consumer<? super T> consumer);

    <T extends Event> Operation<EventSubscription<T>> subscribe(Object object, Class<T> clazz, Consumer<? super T> consumer);

    <T extends Event> Operation<Set<EventSubscription<T>>> subscriptions(Class<T> clazz);

}
