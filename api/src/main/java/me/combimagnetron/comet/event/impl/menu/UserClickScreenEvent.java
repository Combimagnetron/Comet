package me.combimagnetron.comet.event.impl.menu;

import me.combimagnetron.comet.event.Event;
import me.combimagnetron.comet.internal.Item;
import me.combimagnetron.comet.game.menu.Menu;
import me.combimagnetron.comet.user.User;

public record UserClickScreenEvent(User<?> user, Menu menu, Item.Slot slot) implements Event {

    public static UserClickScreenEvent of(User<?> user, Menu menu, Item.Slot slot) {
        return new UserClickScreenEvent(user, menu, slot);
    }

    @Override
    public Class<? extends Event> eventType() {
        return null;
    }
}
