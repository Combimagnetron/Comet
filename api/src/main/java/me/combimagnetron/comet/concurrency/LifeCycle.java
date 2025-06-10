package me.combimagnetron.comet.concurrency;

import me.combimagnetron.comet.util.Progress;

public interface LifeCycle {
    void stop();

    void pause();

    void resume();

    Progress progress();

}