package me.combimagnetron.lagoon.feature.menu;

import me.combimagnetron.lagoon.feature.ExternalFeatureComponent;
import me.combimagnetron.lagoon.feature.Feature;

public abstract class ExternalMenuFeatureComponent extends ExternalFeatureComponent {
    public ExternalMenuFeatureComponent(Feature feature) {
        super(feature, OutsourceReason.VERSION_DEPENDENT_IMPLEMENTATION);
    }
}
