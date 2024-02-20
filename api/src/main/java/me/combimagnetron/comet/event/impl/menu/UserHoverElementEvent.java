package me.combimagnetron.comet.event.impl.menu;

import me.combimagnetron.comet.event.Event;
import me.combimagnetron.comet.feature.menu.element.Element;
import me.combimagnetron.comet.user.User;

public record UserHoverElementEvent(User<?> user, Element element) implements Event {

    public static UserHoverElementEvent of(User<?> user, Element element) {
        return new UserHoverElementEvent(user, element);
    }

    @Override
    public Class<? extends Event> eventType() {
        return null;
    }
}
