package me.combimagnetron.comet.command;

import me.combimagnetron.comet.condition.Condition;
import org.jetbrains.annotations.Nullable;

public interface InternalSubCommand {

    Format format();

    @Nullable Condition condition();

    record Impl(Format format, Condition condition) implements InternalSubCommand {
        public static Impl of(Format format, Condition condition) {
            return new Impl(format, condition);
        }

    }

}
