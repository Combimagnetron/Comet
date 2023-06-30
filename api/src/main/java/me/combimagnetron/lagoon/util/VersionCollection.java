package me.combimagnetron.lagoon.util;

import java.util.ArrayList;
import java.util.HashSet;

public class VersionCollection<E> extends ArrayList<E> {

    public E newest() {
        return get(0);
    }

    public E oldest() {
        return get(size() - 1);
    }

}
