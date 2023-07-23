package me.combimagnetron.lagoon.moderation;

import me.combimagnetron.lagoon.user.User;

import java.util.concurrent.TimeUnit;

public abstract class Punishment {
    private final User<?> player;
    private final Duration duration;

    public Punishment(User<?> player, Duration duration) {
        this.player = player;
        this.duration = duration;
        handle();
    }

    abstract void handle();

    public User<?> player() {
        return this.player;
    }

    public Duration duration() {
        return this.duration;
    }

    public record Duration(long time, TimeUnit unit) {
    }
}
