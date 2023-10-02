package me.combimagnetron.comet.feature.menu.style;

import me.combimagnetron.comet.feature.menu.snapshot.Snapshot;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public interface Style {

    Map<String, StyleFeature> appliedFeatures();

    void apply(String label, StyleFeature styleFeature);

    interface StyleFeature {
        Component apply(Snapshot<Component> snapshot);
    }

    static Style style() {
        return new StyleImpl();
    }

    class StyleImpl implements Style {
        private final Map<String, StyleFeature> appliedFeatures = new HashMap<>();

        @Override
        public Map<String, StyleFeature> appliedFeatures() {
            return appliedFeatures;
        }

        @Override
        public void apply(String label, StyleFeature styleFeature) {
            appliedFeatures.put(label, styleFeature);
        }
    }

}
