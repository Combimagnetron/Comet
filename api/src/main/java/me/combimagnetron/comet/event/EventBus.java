package me.combimagnetron.comet.event;

import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.event.impl.internal.MessageEvent;
import me.combimagnetron.comet.event.impl.internal.PacketEvent;
import me.combimagnetron.comet.internal.network.packet.Packet;

import java.util.function.Consumer;

public interface EventBus {

    static <T extends Event> EventSubscription<T> subscribe(Class<T> clazz, EventFilter<T, ?> filter, Consumer<? super T> consumer) {
        return new EventSubscription.FilteredImpl<>(clazz, filter, consumer);
    }

    static <T extends Event> EventSubscription<T> subscribe(Class<T> clazz, Consumer<? super T> consumer) {
        return new EventSubscription.Impl<>(clazz, consumer);
    }

    static <M extends Message, T extends MessageEvent<M>> EventSubscription<T> message(Class<M> clazz, Consumer<? super T> consumer) {
        return new EventSubscription.MessageImpl<>(clazz, consumer);
    }

    static <P extends Packet, T extends PacketEvent<P>> EventSubscription<T> packet(Class<P> clazz, Consumer<? super T> consumer) {
        return new EventSubscription.PacketImpl<>(clazz, consumer);
    }

}
