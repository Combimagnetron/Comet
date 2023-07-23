package me.combimagnetron.lagoon.feature.menu;

import me.combimagnetron.lagoon.feature.menu.draft.Draft;
import me.combimagnetron.lagoon.feature.menu.element.Div;
import me.combimagnetron.lagoon.feature.menu.element.Popup;
import me.combimagnetron.lagoon.feature.menu.element.Weight;
import me.combimagnetron.lagoon.feature.menu.style.Style;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.user.User;

import java.util.Collection;
import java.util.TreeMap;

public abstract class AdvancedMenu implements Menu {
    private final TreeMap<Weight, Div> divTreeMap = new TreeMap<>();
    private final Style style = Style.style();

    public abstract <T,V> Operation<V> run(Draft<T, V> draft);

    public abstract void popup(Popup popup);

    public Collection<User<?>> viewers() {
        return null;
    }

    public Collection<Div> elements() {
        return divTreeMap.values();
    }

    public Style style() {
        return style;
    }

    public abstract void update(Draft draft);

}