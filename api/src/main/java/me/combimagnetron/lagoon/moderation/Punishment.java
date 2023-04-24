package me.combimagnetron.lagoon.moderation;

import me.combimagnetron.lagoon.player.GlobalPlayer;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class Punishment {
    private final GlobalPlayer<?> player;
    private final Duration duration;

    public Punishment(GlobalPlayer<?> player, Duration duration) {
        this.player = player;
        this.duration = duration;
        handle();
    }

    abstract void handle();

    public GlobalPlayer<?> player() {
        return this.player;
    }

    public Duration duration() {
        return this.duration;
    }

    public record Duration(long time, TimeUnit unit) {
    }
}
