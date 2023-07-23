package me.combimagnetron.lagoon.command;

import me.combimagnetron.lagoon.command.argument.Argument;

public record Format(String string, Argument<?>... arguments) {
    public static Format format(String string, Argument<?>... arguments) {
        return new Format(string, arguments);
    }

}
