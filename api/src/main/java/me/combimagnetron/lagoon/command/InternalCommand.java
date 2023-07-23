package me.combimagnetron.lagoon.command;

import me.combimagnetron.lagoon.command.annotations.SubCommand;
import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.command.argument.impl.StringArgument;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public interface InternalCommand {

    Format format();

    Collection<InternalSubCommand> subCommands();

    Operation<Void> run(User<?> user, Argument<?>... arguments);

    ReflectionInfo reflectionInfo();

    record Impl(Format format, Collection<InternalSubCommand> subCommands, ReflectionInfo reflectionInfo) implements InternalCommand {
        static Impl of(Format format, Collection<InternalSubCommand> subCommands, ReflectionInfo reflectionInfo) {
            return new Impl(format, subCommands, reflectionInfo);
        }

        @Override
        public Operation<Void> run(User<?> user, Argument<?>... arguments) {
            return Operation.simple(() -> {
                try {
                    final StringBuilder builder = new StringBuilder();
                    for (Class<?> parameterType : reflectionInfo.execute.getParameterTypes()) {
                        builder.append(parameterType.getTypeName());
                    }
                    Bukkit.getLogger().info(builder.toString() + " " + reflectionInfo.execute.getParameterCount() + "\n" + Arrays.toString(arguments));
                    reflectionInfo.execute.invoke(reflectionInfo.clazz.getDeclaredConstructor().newInstance(), user, new StringArgument("frog.bbmodel"));
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    record ReflectionInfo(Method execute, Class<?> clazz) {
        public static ReflectionInfo of(Method execute, Class<?> clazz) {
            return new ReflectionInfo(execute, clazz);
        }
    }

}
