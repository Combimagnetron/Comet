package me.combimagnetron.lagoon.feature.menu;

import me.combimagnetron.lagoon.feature.menu.draft.Draft;
import me.combimagnetron.lagoon.feature.menu.element.Div;
import me.combimagnetron.lagoon.feature.menu.element.Popup;
import me.combimagnetron.lagoon.feature.menu.element.Weight;
import me.combimagnetron.lagoon.operation.Operation;

import java.util.TreeMap;

public abstract class AdvancedMenu implements Menu {
    private final TreeMap<Weight, Div> divTreeMap = new TreeMap<>();

    public abstract <T,V> Operation<V> run(Draft<T, V> draft);

    public void popup(Popup popup) {

    }

}