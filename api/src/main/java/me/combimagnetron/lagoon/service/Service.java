package me.combimagnetron.lagoon.service;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.service.config.Parameter;

import java.util.Set;

public interface Service {

    Identifier identifier();

    void stop();

    void start();

}
