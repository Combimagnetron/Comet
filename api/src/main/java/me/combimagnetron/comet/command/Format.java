package me.combimagnetron.comet.command;

import me.combimagnetron.comet.command.argument.Argument;

import java.util.List;

public record Format(String string, List<String> aliases, Argument<?>... arguments) {
    public static Format format(String string, List<String> aliases, Argument<?>... arguments) {
        return new Format(string, aliases, arguments);
    }

}
