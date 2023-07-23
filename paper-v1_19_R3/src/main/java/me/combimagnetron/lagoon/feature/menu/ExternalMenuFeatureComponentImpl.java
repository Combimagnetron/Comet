package me.combimagnetron.lagoon.feature.menu;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.service.Action;

import java.util.Set;

public class ExternalMenuFeatureComponentImpl extends ExternalMenuFeatureComponent {
    public ExternalMenuFeatureComponentImpl(Feature feature) {
        super(feature);
    }

    @Override
    public Identifier identifier() {
        return null;
    }

    @Override
    public Operation<Void> restart() {
        return null;
    }

    @Override
    public Set<Class<? extends Action<?, ?>>> actions() {
        return null;
    }

    @Override
    public FeatureOutsourceReasonParameter config() {
        return null;
    }
}
