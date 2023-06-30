package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.service.Action;
import me.combimagnetron.lagoon.service.Service;
import me.combimagnetron.lagoon.service.config.Parameter;

import java.util.Set;

public class KiwiScriptService implements Service {


    @Override
    public Identifier identifier() {
        return null;
    }

    @Override
    public Operation<Void> restart() {
        return Operation.simple(() -> {

        });
    }

    @Override
    public Set<Class<? extends Action<?, ?>>> actions() {
        return null;
    }

    @Override
    public Parameter<?, ?> config() {
        return null;
    }
}
