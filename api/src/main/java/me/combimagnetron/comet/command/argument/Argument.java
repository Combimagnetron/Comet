package me.combimagnetron.comet.command.argument;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.user.User;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public interface Argument<T> {
    Argument<Integer> INTEGER = of(Integer::parseInt);
    Argument<User<?>> USER = of(string -> CometBase.comet().users().users().stream().filter(user -> Objects.equals(user.name(), string)).findAny().get());
    Argument<Double> DOUBLE = of(Double::parseDouble);
    Argument<String> STRING = of(string -> string);

    T value(String string);

    Collection<String> completions();

    static <V> Argument<V> of(Function<String, V> function) {
        return new Impl<>(function);
    }

    class Impl<T> implements Argument<T> {
        private final Function<String, T> function;

        protected Impl(Function<String, T> function) {
            this.function = function;
        }

        @Override
        public T value(String string) {
            return function.apply(string);
        }

        @Override
        public Collection<String> completions() {
            return List.of();
        }
    }

    class Lookup {
        public static <T> @Nullable Argument<T> get(Class<?> clazz) {
            final String name = clazz.getName().toUpperCase();
            for (Field field : Argument.class.getDeclaredFields()) {
                if (!(field.getGenericType() instanceof ParameterizedType)) {
                    continue;
                }
                try {
                    return (Argument<T>) field.get(name);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }

    }


}
