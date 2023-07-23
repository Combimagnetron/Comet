package me.combimagnetron.lagoon.command;

import me.combimagnetron.lagoon.command.annotations.Command;
import me.combimagnetron.lagoon.command.annotations.Execute;
import me.combimagnetron.lagoon.command.annotations.SubCommand;
import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.user.User;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class CommandHandler {

    public Set<InternalCommand> commands() {
        String packageName = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getPackageName();
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Command.class);
        Set<InternalCommand> commands = new HashSet<>();
        classSet.forEach(clazz -> {
            InternalCommand internalCommand = internalCommand(clazz);
            commands.add(internalCommand);
        });
        return commands;
    }

    private InternalCommand internalCommand(Class<?> clazz) {
        String command = clazz.getAnnotation(Command.class).command();
        Collection<InternalSubCommand> subCommands = subCommands(clazz);
        Collection<Argument<?>> arguments = arguments(clazz);
        Format format = Format.format(command, arguments.toArray(new Argument<?>[0]));
        Method execute = Arrays.stream(clazz.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Execute.class)).findFirst().orElseThrow();
        return InternalCommand.Impl.of(format, subCommands, InternalCommand.ReflectionInfo.of(execute, clazz));
    }

    private Collection<InternalSubCommand> subCommands(Class<?> clazz) {
        Set<InternalSubCommand> subCommands = new HashSet<>();
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
            if (!method.isAnnotationPresent(SubCommand.class)) {
                return;
            }
            SubCommand subCommand = method.getAnnotation(SubCommand.class);
            Collection<Argument<?>> arguments = arguments(clazz);
            Format format = Format.format(subCommand.command(), arguments.toArray(new Argument<?>[0]));
            subCommands.add(InternalSubCommand.Impl.of(format));
        });
        return subCommands;
    }

    private Collection<Argument<?>> arguments(Class<?> clazz) {
        Set<Argument<?>> arguments = new HashSet<>();
        Method executes = Arrays.stream(clazz.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Execute.class)).findFirst().orElseThrow();
        Class<?>[] parameters = executes.getParameterTypes();
        Arrays.stream(parameters).forEach(parameter -> {
            if (parameter.equals(User.class) || !parameter.isAssignableFrom(Argument.class)) {
                return;
            }
            try {
                arguments.add((Argument<?>) parameter.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
        return arguments;
    }


}
