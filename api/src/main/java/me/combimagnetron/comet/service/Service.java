package me.combimagnetron.comet.service;

import me.combimagnetron.comet.data.Identifier;

public interface Service {

    Identifier identifier();

    void stop();

    void start();

    void tick();

}
