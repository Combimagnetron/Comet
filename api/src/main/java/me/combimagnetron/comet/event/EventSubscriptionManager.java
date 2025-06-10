package me.combimagnetron.comet.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public sealed interface EventSubscriptionManager<V extends Event> permits EventSubscriptionManager.Impl {

    void subscription(EventSubscription<V> subscription);

    Collection<EventSubscription<V>> subscriptions();

    Map<Class<? extends Event>, EventSubscription<V>> subscriptionMap();

    void unsubscribe(EventSubscription<V> vEventSubscription);

    final class Impl<V extends Event> implements EventSubscriptionManager<V> {
        private final Map<Class<? extends Event>, EventSubscription<V>> subscriptions = new ConcurrentHashMap<>();

        @Override
        public void subscription(EventSubscription<V> subscription) {
            subscriptions.put(subscription.getEventClass(), subscription);
        }

        @Override
        public Collection<EventSubscription<V>> subscriptions() {
            return subscriptions.values();
        }

        @Override
        public Map<Class<? extends Event>, EventSubscription<V>> subscriptionMap() {
            return subscriptions;
        }

        @Override
        public void unsubscribe(EventSubscription<V> subscription) {
            subscriptions.remove(subscription.getEventClass(), subscription);
        }


    }

}
