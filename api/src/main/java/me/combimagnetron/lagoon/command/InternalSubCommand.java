package me.combimagnetron.lagoon.command;

import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.condition.Condition;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface InternalSubCommand {

    Format format();

    @Nullable Condition condition();

    record Impl(Format format, Condition condition) implements InternalSubCommand {
        public static Impl of(Format format, Condition condition) {
            return new Impl(format, condition);
        }

    }

}
