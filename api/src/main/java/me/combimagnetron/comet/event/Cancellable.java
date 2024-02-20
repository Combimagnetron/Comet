package me.combimagnetron.comet.event;

public interface Cancellable {

    boolean cancelled();

    void cancel(boolean bool);

    default void cancel() {
        cancel(true);
    }

}
