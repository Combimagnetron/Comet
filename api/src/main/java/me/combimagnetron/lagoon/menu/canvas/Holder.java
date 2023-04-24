package me.combimagnetron.lagoon.menu.canvas;

import me.combimagnetron.lagoon.menu.canvas.metadata.Tickable;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.operation.Result;

import java.util.*;

public abstract class Holder<E extends Tickable> {
    private final Set<E> set = new LinkedHashSet<>();

    public Operation<Boolean> add(E e) {
        return null;
    }

    @SafeVarargs
    public final Operation<Boolean> add(E... e) {
        return null;
    }

    public Operation<Collection<Result>> tickElements() {
        Collection<Result> results = new ArrayList<>();
        for (E e : set.stream().toList()) {
            Operation<Boolean> booleanOperation = e.tick();
            results.add(null);
        }
        return null;
    }


    public Collection<E> all() {
        return set;
    }


}
