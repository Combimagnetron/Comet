package me.combimagnetron.comet.feature.menu;

import me.combimagnetron.comet.feature.menu.draft.Draft;
import me.combimagnetron.comet.feature.menu.element.Div;
import me.combimagnetron.comet.feature.menu.element.Weight;
import me.combimagnetron.comet.feature.menu.style.Style;
import me.combimagnetron.comet.feature.menu.element.Popup;
import me.combimagnetron.comet.operation.Operation;
import me.combimagnetron.comet.user.User;

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