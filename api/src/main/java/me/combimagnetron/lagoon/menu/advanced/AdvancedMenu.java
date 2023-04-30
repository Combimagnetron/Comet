package me.combimagnetron.lagoon.menu.advanced;

import me.combimagnetron.lagoon.menu.Menu;
import me.combimagnetron.lagoon.menu.advanced.draft.Draft;
import me.combimagnetron.lagoon.menu.advanced.element.Div;
import me.combimagnetron.lagoon.menu.advanced.element.Popup;
import me.combimagnetron.lagoon.menu.advanced.element.Weight;
import me.combimagnetron.lagoon.operation.Operation;

import java.util.TreeMap;

public abstract class AdvancedMenu implements Menu {
    private final TreeMap<Weight, Div> divTreeMap = new TreeMap<>();

    public abstract <T,V> Operation<V> run(Draft<T, V> draft);

    public void popup(Popup popup) {

    }

}
