package me.combimagnetron.lagoon.command.argument.impl;

import me.combimagnetron.lagoon.command.argument.Argument;

import java.util.Collection;
import java.util.List;

public class StringArgument implements Argument<String> {
    private final String string;

    public StringArgument(String string) {
        this.string = string;
    }

    @Override
    public Class<?> type() {
        return String.class;
    }

    @Override
    public String value() {
        return string;
    }

    @Override
    public Collection<String> completions() {
        return List.of();
    }
}
