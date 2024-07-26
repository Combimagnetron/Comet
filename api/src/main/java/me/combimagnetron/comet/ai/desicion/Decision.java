package me.combimagnetron.comet.ai.desicion;

import me.combimagnetron.comet.ai.desicion.influence.Influence;
import me.combimagnetron.comet.ai.desicion.influence.InfluenceEvaluator;

import java.util.Objects;
import java.util.TreeSet;

public final class Decision {
    private final String id;
    private final double weight;
    private final TreeSet<Influence> influenceTreeSet;
    private final InfluenceEvaluator influenceEvaluator;

    public Decision(String id, double weight, TreeSet<Influence> influenceTreeSet) {
        this.id = id;
        this.weight = weight;
        this.influenceTreeSet = influenceTreeSet;
        this.influenceEvaluator = new InfluenceEvaluator(influenceTreeSet);
    }

    public String id() {
        return id;
    }

    public double weight() {
        return weight;
    }

    public double netWeight() {
        return influenceEvaluator.evaluate(weight);
    }

    public TreeSet<Influence> influenceTreeSet() {
        return influenceTreeSet;
    }

}
