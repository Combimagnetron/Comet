package me.combimagnetron.comet.feature.menu.draft;

import me.combimagnetron.comet.operation.Operation;

public interface Draft <V, T> {

    Operation<Void> supply(V v, T t);

    Operation<V> finish();

    Operation<V> supplyAndFinish(V v, T t);

}