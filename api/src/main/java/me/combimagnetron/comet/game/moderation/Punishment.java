package me.combimagnetron.comet.game.moderation;

import me.combimagnetron.comet.user.User;

import java.util.concurrent.TimeUnit;

public abstract class Punishment {
    private final User<?> user;
    private final Duration duration;

    public Punishment(User<?> user, Duration duration) {
        this.user = user;
        this.duration = duration;
        handle();
    }

    abstract void handle();

    public User<?> user() {
        return this.user;
    }

    public Duration duration() {
        return this.duration;
    }

    public record Duration(long time, TimeUnit unit) {
    }
}
