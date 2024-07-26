package me.combimagnetron.comet.event;

import java.util.function.Consumer;

sealed public interface EventSubscription<V extends Event> permits EventSubscription.Impl, EventSubscription.FilteredImpl {

    Consumer<? super V> handler();

    Class<V> getEventClass();

    void close();

    boolean active();

    final class Impl<V extends Event> implements EventSubscription<V> {
        private final Class<V> eventClass;
        private final Consumer<? super V> handler;
        private boolean active;

        Impl(Class<V> eventClass, Consumer<? super V> handler) {
            this.eventClass = eventClass;
            this.handler = handler;
            if (eventClass.isAssignableFrom(Event.FilteredEvent.class)) {
                throw new IllegalArgumentException("FilteredEvent implementations can only be used with EventFilters present!");
            }
        }

        @Override
        public Consumer<? super V> handler() {
            return handler;
        }

        @Override
        public Class<V> getEventClass() {
            return eventClass;
        }

        @Override
        public void close() {
            this.active = false;

        }

        @Override
        public boolean active() {
            return active;
        }
    }

    final class FilteredImpl<V extends Event> implements EventSubscription<V> {
        private final Class<V> eventClass;
        private final Consumer<? super V> handler;
        private boolean active;

        FilteredImpl(Class<V> eventClass, EventFilter filter, Consumer<? super V> handler) {
            this.eventClass = eventClass;
            this.handler = handler;
        }

        @Override
        public Consumer<? super V> handler() {
            return handler;
        }

        @Override
        public Class<V> getEventClass() {
            return eventClass;
        }

        @Override
        public void close() {
            this.active = false;

        }

        @Override
        public boolean active() {
            return active;
        }
    }

}
