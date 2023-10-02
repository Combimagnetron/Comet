package me.combimagnetron.comet.feature.menu.snapshot;

import java.util.Collection;

public interface Snapshot<T> {

    Collection<T> previousIterations();

    T iteration();

    Snapshot<T> update(T t);

}
