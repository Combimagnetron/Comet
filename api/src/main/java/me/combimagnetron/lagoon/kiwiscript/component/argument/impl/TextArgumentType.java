package me.combimagnetron.lagoon.kiwiscript.component.argument.impl;

import me.combimagnetron.lagoon.kiwiscript.component.argument.ArgumentType;

public class TextArgumentType extends ArgumentType<String> {
    public TextArgumentType(String name) {
        super(name, String.class);
    }
}