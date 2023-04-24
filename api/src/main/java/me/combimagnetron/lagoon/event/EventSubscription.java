package me.combimagnetron.lagoon.event;

import java.util.function.Consumer;

public interface EventSubscription<V extends Event> extends AutoCloseable {

    Consumer<? super V> handler();

    Class<V> getEventClass();

    @Override
    void close() throws Exception;

    boolean active();
}
