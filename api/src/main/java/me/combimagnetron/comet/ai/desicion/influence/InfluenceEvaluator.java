package me.combimagnetron.comet.ai.desicion.influence;

import java.util.TreeSet;

public class InfluenceEvaluator {
    private final TreeSet<Influence> influenceTreeSet;

    public InfluenceEvaluator(TreeSet<Influence> influenceTreeSet) {
        this.influenceTreeSet = influenceTreeSet;
    }

    public double evaluate(double start) {
        double result = start;
        for (Influence influence : influenceTreeSet) {
            result = influence.equation().function().evaluate(result);
        }
        return start;
    }


}
