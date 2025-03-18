package me.combimagnetron.pilot;

import me.combimagnetron.comet.service.Deployment;
import me.combimagnetron.comet.service.DeploymentRegistry;

import java.util.concurrent.ConcurrentHashMap;

public class DeploymentRegistryImpl implements DeploymentRegistry {
    private final ConcurrentHashMap<String, Deployment> deployment = new ConcurrentHashMap<>();

    @Override
    public Deployment deployment(String name) {
        return deployment.get(name);
    }

    public void add(Deployment deployment) {
        this.deployment.put(deployment.name().string(), deployment);
    }

}
