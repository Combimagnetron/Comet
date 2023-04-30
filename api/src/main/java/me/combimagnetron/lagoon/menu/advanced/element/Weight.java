package me.combimagnetron.lagoon.menu.advanced.element;

import org.jetbrains.annotations.NotNull;

public class Weight implements Comparable<Weight> {
    private final double weight;

    protected Weight(double weight) {
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    @Override
    public int compareTo(@NotNull Weight o) {
        return (int) (weight - o.weight);
    }
}
