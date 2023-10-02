package me.combimagnetron.comet.command;

import me.combimagnetron.comet.command.annotations.Command;
import me.combimagnetron.comet.command.annotations.Execute;
import me.combimagnetron.comet.command.annotations.Requires;
import me.combimagnetron.comet.command.annotations.SubCommand;
import me.combimagnetron.comet.command.argument.Argument;
import me.combimagnetron.comet.condition.Condition;
import me.combimagnetron.comet.user.User;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.*;

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
        Command command = clazz.getAnnotation(Command.class);
        String name = command.command();
        List<String> aliases = Arrays.stream(command.aliases()).toList();
        Collection<InternalSubCommand> subCommands = subCommands(clazz);
        Collection<Argument<?>> arguments = arguments(clazz);
        Format format = Format.format(name, aliases, arguments.toArray(new Argument<?>[0]));
        Method execute = Arrays.stream(clazz.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Execute.class)).findFirst().orElseThrow();
        Optional<Condition> condition = requirement(clazz);
        return InternalCommand.Impl.of(format, subCommands, InternalCommand.ReflectionInfo.of(execute, clazz), condition.get());
    }

    private Collection<InternalSubCommand> subCommands(Class<?> clazz) {
        Set<InternalSubCommand> subCommands = new HashSet<>();
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
            if (!method.isAnnotationPresent(SubCommand.class)) {
                return;
            }
            SubCommand subCommand = method.getAnnotation(SubCommand.class);
            Collection<Argument<?>> arguments = arguments(clazz);
            List<String> aliases = Arrays.stream(subCommand.aliases()).toList();
            Format format = Format.format(subCommand.command(), aliases, arguments.toArray(new Argument<?>[0]));
            Optional<Condition> condition = requirement(method);
            subCommands.add(InternalSubCommand.Impl.of(format, condition.get()));
        });
        return subCommands;
    }

    private Collection<Argument<?>> arguments(Class<?> clazz) {
        Set<Argument<?>> arguments = new HashSet<>();
        Method executes = Arrays.stream(clazz.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Execute.class)).findFirst().orElseThrow();
        Class<?>[] parameters = executes.getParameterTypes();
        Arrays.stream(parameters).forEach(parameter -> {
            if (parameter.equals(User.class)) {
                return;
            }
            arguments.add(Argument.Lookup.get(parameter));
        });
        return arguments;
    }

    private Optional<Condition> requirement(Method method) {
        if (!method.isAnnotationPresent(Requires.class)) {
            return Optional.empty();
        }
        return Optional.of(Condition.of(method.getAnnotation(Requires.class).condition()));
    }

    private Optional<Condition> requirement(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Requires.class)) {
            return Optional.empty();
        }
        return Optional.of(Condition.of(clazz.getAnnotation(Requires.class).condition()));
    }


}
