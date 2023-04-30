package me.combimagnetron.lagoon.menu.advanced.draft;

import me.combimagnetron.lagoon.operation.Operation;

public interface Draft <V, T> {

    Operation<Void> supply(V v, T t);

    Operation<V> finish();

    Operation<V> supplyAndFinish(V v, T t);

}