package me.combimagnetron.lagoon.kiwiscript.component.variable;

public abstract class Variable {
    private final String[] aliases;

    protected Variable(Class<?> clazz, String... aliases) {
        this.aliases = aliases;
    }



}
