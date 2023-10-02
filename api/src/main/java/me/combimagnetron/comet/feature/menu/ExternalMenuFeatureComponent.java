package me.combimagnetron.comet.feature.menu;

import me.combimagnetron.comet.feature.ExternalFeatureComponent;
import me.combimagnetron.comet.feature.Feature;

public abstract class ExternalMenuFeatureComponent extends ExternalFeatureComponent {
    public ExternalMenuFeatureComponent(Feature feature) {
        super(feature, OutsourceReason.VERSION_DEPENDENT_IMPLEMENTATION);
    }
}
