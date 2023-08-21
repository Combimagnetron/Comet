package me.combimagnetron.lagoon.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class VersionCollection<E> extends ArrayList<E> {

    @SafeVarargs
    public static <E> VersionCollection<E> of(E... es) {
        VersionCollection<E> versionCollection = new VersionCollection<>();
        versionCollection.addAll(List.of(es));
        return versionCollection;
    }

    public E newest() {
        return get(0);
    }

    public E oldest() {
        return get(size() - 1);
    }

}
