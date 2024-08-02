package me.combimagnetron.comet.game.entity.animation;

public class Animation {
    private final Timeline timeline = new Timeline();
    private final boolean loop;

    protected Animation(boolean loop) {
        this.loop = loop;
    }

    public void tick() {

    }

    public boolean loops() {
        return loop;
    }

    public Timeline timeline() {
        return timeline;
    }


}
