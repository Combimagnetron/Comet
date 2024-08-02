package me.combimagnetron.comet.event.impl.menu;

import me.combimagnetron.comet.event.Cancellable;
import me.combimagnetron.comet.event.Event;
import me.combimagnetron.comet.game.menu.Menu;
import me.combimagnetron.comet.user.User;

public final class UserCloseScreenEvent implements MenuEvent, Cancellable {
    private final User<?> user;
    private final Menu menu;
    private boolean cancelled = false;

    public UserCloseScreenEvent(User<?> user, Menu screen) {
        this.user = user;
        this.menu = screen;
    }

    public static UserCloseScreenEvent of(User<?> user, Menu menu) {
        return new UserCloseScreenEvent(user, menu);
    }

    @Override
    public boolean cancelled() {
        return cancelled;
    }

    @Override
    public void cancel(boolean bool) {
        this.cancelled = bool;
        if (!bool) {
            return;
        }
        //Scheduler.delayTick(() -> user.show(menu));
    }

    @Override
    public void cancel() {
        Cancellable.super.cancel();
    }

    public User<?> user() {
        return user;
    }

    public Menu menu() {
        return menu;
    }

    @Override
    public Class<? extends Event> eventType() {
        return UserCloseScreenEvent.class;
    }
}
