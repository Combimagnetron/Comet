package me.combimagnetron.lagoon.event;

import me.combimagnetron.lagoon.event.impl.spigot.SpigotEvent;
import me.combimagnetron.lagoon.operation.Operation;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Set;
import java.util.function.Consumer;

public interface EventBus {

    /*static <V extends Event> Operation<EventSubscription<V>> subscribe(Class<V> clazz, Consumer<? super V> consumer) {
        return Operation.executable(() -> new EventSubscription.Impl<>(clazz, consumer));
    }*/

    static <T extends org.bukkit.event.Event> Operation<EventSubscription.SpigotImpl> subscribe(Class<T> clazz, Consumer<T> consumer) {
        return Operation.executable(() -> new EventSubscription.SpigotImpl((Class<? extends org.bukkit.event.Event>) clazz, (Consumer<org.bukkit.event.Event>) consumer));
    }

    static <T extends Event> Operation<EventSubscription<T>> subscribe(Class<T> clazz, EventFilter<T, ?> filter, Consumer<? super T> consumer) {
        return Operation.executable(() -> new EventSubscription.FilteredImpl<>(clazz, filter, consumer));
    }

    static <T extends Event> Operation<Set<EventSubscription<T>>> subscriptions(Class<T> clazz) {
        return Operation.executable(() -> null);
    }


}
