package me.combimagnetron.comet.feature;

import me.combimagnetron.comet.service.Service;

public abstract class ExternalFeatureComponent implements Service {
    private final Feature feature;
    private final OutsourceReason outsourceReason;

    public ExternalFeatureComponent(Feature feature, OutsourceReason outsourceReason) {
        this.feature = feature;
        this.outsourceReason = outsourceReason;
    }

    public Feature feature() {
        return feature;
    }

    public OutsourceReason outsourceReason() {
        return outsourceReason;
    }

    public enum OutsourceReason {
        VERSION_DEPENDENT_IMPLEMENTATION, EXTERNAL_SERVICE, MULTI_PLATFORM_FEATURE
    }

}
