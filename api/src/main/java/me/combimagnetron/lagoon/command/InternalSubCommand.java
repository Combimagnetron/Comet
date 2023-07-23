package me.combimagnetron.lagoon.command;

import me.combimagnetron.lagoon.command.argument.Argument;

import java.util.Collection;

public interface InternalSubCommand {

    Format format();

    record Impl(Format format) implements InternalSubCommand {
        public static Impl of(Format format) {
            return new Impl(format);
        }
    }

}
