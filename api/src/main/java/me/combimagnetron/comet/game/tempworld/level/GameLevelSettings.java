package me.combimagnetron.comet.game.tempworld.level;

import me.combimagnetron.comet.game.tempworld.GameMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GameLevelSettings implements Serializable {
    private final Map<Flag, Boolean> flags = new HashMap<>();
    private GameMode gameMode = GameMode.ADVENTURE;

    protected GameLevelSettings(GameMode defaultMode, Map<Flag, Boolean> flags) {
        this.flags.putAll(flags);
        this.gameMode = defaultMode;
    }

    private GameLevelSettings() {}

    public boolean flagState(Flag flag) {
        return flags.getOrDefault(flag, flag.val);
    }

    public GameMode defaultMode() {
        return gameMode;
    }

    public enum Flag {
        MOB_SPAWNS(false), BUILDING(false), PVP(false), FALL_DAMAGE(false);

        private final boolean val;

        Flag(boolean defaultVal) {
            this.val = defaultVal;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GameMode defaultMode = GameMode.ADVENTURE;
        private Map<Flag, Boolean> flags = new HashMap<>();

        public Builder flag(Flag flag, boolean bool) {
            flags.put(flag, bool);
            return this;
        }

        public Builder flag(Map<Flag, Boolean> flags) {
            flags.putAll(flags);
            return this;
        }

        public Builder gameMode(GameMode defaultMode) {
            this.defaultMode = defaultMode;
            return this;
        }

        public GameLevelSettings build() {
            return new GameLevelSettings(defaultMode, flags);
        }

    }

}
