package me.combimagnetron.comet.event.impl.internal;

import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.event.Event;

public record MessageEvent<M extends Message>(M message) implements Event {

    @Override
    public Class<? extends Event> eventType() {
        return MessageEvent.class;
    }
}
