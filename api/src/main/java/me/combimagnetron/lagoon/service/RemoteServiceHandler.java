package me.combimagnetron.lagoon.service;

import me.combimagnetron.lagoon.data.Identifier;

import java.util.concurrent.ConcurrentHashMap;

public class RemoteServiceHandler implements ServiceHandler {
    private final ConcurrentHashMap<Identifier, Service> map = new ConcurrentHashMap<>();

    @Override
    public Service service(Identifier identifier) {
        return map.get(identifier);
    }

    @Override
    public Service deploy(ServiceBlueprint blueprint, Identifier identifier) {
        return null;
    }
}
