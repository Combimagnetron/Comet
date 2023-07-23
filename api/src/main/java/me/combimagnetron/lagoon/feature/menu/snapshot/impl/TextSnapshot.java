package me.combimagnetron.lagoon.feature.menu.snapshot.impl;

import me.combimagnetron.lagoon.feature.menu.snapshot.Snapshot;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.HashSet;

public class TextSnapshot implements Snapshot<Component> {
    private Collection<Component> previousIterations = new HashSet<>();
    private Component iteration;
    @Override
    public Collection<Component> previousIterations() {
        return previousIterations;
    }

    @Override
    public Component iteration() {
        return iteration;
    }

    @Override
    public Snapshot<Component> update(Component component) {
        this.previousIterations.add(iteration);
        this.iteration = component;
        return this;
    }
}
