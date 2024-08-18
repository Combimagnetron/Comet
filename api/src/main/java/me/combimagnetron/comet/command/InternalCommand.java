package me.combimagnetron.comet.command;

import me.combimagnetron.comet.util.condition.Condition;
import me.combimagnetron.comet.user.User;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public interface InternalCommand {

    Format format();

    Collection<InternalSubCommand> subCommands();

    void run(User<?> user, Object... objects);

    @Nullable Condition condition();

    ReflectionInfo reflectionInfo();

    record Impl(Format format, Collection<InternalSubCommand> subCommands, ReflectionInfo reflectionInfo, Condition condition) implements InternalCommand {
        static Impl of(Format format, Collection<InternalSubCommand> subCommands, ReflectionInfo reflectionInfo, Condition condition) {
            return new Impl(format, subCommands, reflectionInfo, condition);
        }

        @Override
        public void run(User<?> user, Object... objects) {
            /*if (subCommands.stream().map(command -> command.format().string()).anyMatch(string -> string == objects[0])) {
                subCommands.stream().filter(command -> command.format().string() == objects[0]).findAny().orElseThrow();
            }*/

            try {
                check();
                final StringBuilder builder = new StringBuilder();
                for (Class<?> parameterType : reflectionInfo.execute.getParameterTypes()) {
                    builder.append(parameterType.getTypeName());
                }
                if (objects.length > 0) {
                    reflectionInfo.execute.invoke(reflectionInfo.clazz.getDeclaredConstructor().newInstance(), user, objects);
                } else {
                    reflectionInfo.execute.invoke(reflectionInfo.clazz.getDeclaredConstructor().newInstance(), user);
                }


            } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
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
