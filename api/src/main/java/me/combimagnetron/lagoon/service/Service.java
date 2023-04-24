package me.combimagnetron.lagoon.service;

import me.combimagnetron.lagoon.service.config.Parameter;

import java.util.Set;

public interface Service<P extends Parameter<?, ?>> {

    void restart();

    Set<Class<? extends Action<?, ?>>> actions();

    Parameter<?, ?> config();

}
