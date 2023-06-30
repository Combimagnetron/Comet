package me.combimagnetron.lagoon.kiwiscript.component.argument.impl;

import me.combimagnetron.lagoon.kiwiscript.component.argument.ArgumentType;

public class NumberArgumentType extends ArgumentType<Number> {
    public NumberArgumentType(String name) {
        super(name, Number.class);
    }
}