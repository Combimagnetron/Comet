package me.combimagnetron.comet.game.tempworld;

public enum GameMode {
    SURVIVAL(0), CREATIVE(1), ADVENTURE(2), SPECTATOR(3);

    private final int raw;

    GameMode(int raw) {
        this.raw = raw;
    }

    public int raw() {
        return raw;
    }
}
