package me.combimagnetron.comet.event.impl.connection;

import me.combimagnetron.comet.event.Event;
import me.combimagnetron.comet.user.User;

public record UserJoinEvent(User<?> user) implements Event {

    @Override
    public Class<? extends Event> eventType() {
        return UserJoinEvent.class;
    }
}
