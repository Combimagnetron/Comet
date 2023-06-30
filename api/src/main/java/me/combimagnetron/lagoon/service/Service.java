package me.combimagnetron.lagoon.service;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.service.config.Parameter;

import java.util.Set;

public interface Service<P extends Parameter<?, ?>> {

    Identifier identifier();

    Operation<Void> restart();

    Set<Class<? extends Action<?, ?>>> actions();

    P config();

}
