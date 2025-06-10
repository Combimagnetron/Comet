package me.combimagnetron.comet.event.impl.menu;

import me.combimagnetron.comet.event.Event;
import me.combimagnetron.comet.game.menu.element.Element;
import me.combimagnetron.comet.user.User;

public record UserClickElementEvent(User<?> user, Element element) implements Event {

    public static UserClickElementEvent of(User<?> user, Element element) {
        return new UserClickElementEvent(user, element);
    }

    @Override
    public Class<? extends Event> eventType() {
        return null;
    }
}
