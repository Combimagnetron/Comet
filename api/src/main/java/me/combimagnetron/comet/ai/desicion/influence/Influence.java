package me.combimagnetron.comet.ai.desicion.influence;

import me.combimagnetron.comet.ai.environment.Environment;
import me.combimagnetron.comet.ai.math.Equation;
import me.combimagnetron.comet.data.DataContainer;
import org.jetbrains.annotations.NotNull;

public record Influence(Reason reason, Equation equation, Environment environment, DataContainer dataContainer, String id) implements Comparable<Influence> {

    @Override
    public int compareTo(@NotNull Influence o) {
        return (int)(o.equation.function().evaluate(0) - equation.function().evaluate(0));
    }

    public enum Reason {
        OWN_STAT, ENVIRONMENT, OTHER_ENTITY, OTHER
    }

}
