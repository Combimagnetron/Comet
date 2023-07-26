package me.combimagnetron.lagoon.command;

import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.condition.Condition;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface InternalCommand {

    Format format();

    Collection<InternalSubCommand> subCommands();

    Operation<Void> run(User<?> user, Object... objects);

    @Nullable Condition condition();

    ReflectionInfo reflectionInfo();

    record Impl(Format format, Collection<InternalSubCommand> subCommands, ReflectionInfo reflectionInfo, Condition condition) implements InternalCommand {
        static Impl of(Format format, Collection<InternalSubCommand> subCommands, ReflectionInfo reflectionInfo, Condition condition) {
            return new Impl(format, subCommands, reflectionInfo, condition);
        }

        @Override
        public Operation<Void> run(User<?> user, Object... objects) {
            return Operation.simple(() -> {
                try {
                    check();
                    final StringBuilder builder = new StringBuilder();
                    for (Class<?> parameterType : reflectionInfo.execute.getParameterTypes()) {
                        builder.append(parameterType.getTypeName());
                    }
                    Bukkit.getLogger().info(builder.toString() + " " + reflectionInfo.execute.getParameterCount() + "\n" + Arrays.toString(objects));
                    reflectionInfo.execute.invoke(reflectionInfo.clazz.getDeclaredConstructor().newInstance(), user, objects);
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        private void check() {
            if (reflectionInfo.execute.getParameterCount() > 1 && !subCommands().isEmpty()) {
                throw new IllegalArgumentException();
            }
        }

    }

    record ReflectionInfo(Method execute, Class<?> clazz) {
        public static ReflectionInfo of(Method execute, Class<?> clazz) {
            return new ReflectionInfo(execute, clazz);
        }
    }

}
