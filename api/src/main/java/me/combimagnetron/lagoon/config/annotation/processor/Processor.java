package me.combimagnetron.lagoon.config.annotation.processor;

import me.combimagnetron.lagoon.config.Config;
import me.combimagnetron.lagoon.config.annotation.*;
import me.combimagnetron.lagoon.config.element.Node;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Processor {

    public static <T> T load(Class<T> clazz) {
        final Config config = transform(clazz);
        final Set<Class<?>> fields = Arrays.stream(clazz.getEnclosingConstructor().getParameterTypes()).collect(Collectors.toSet());
        final Set<Object> objects = new HashSet<>();
        for (Node<?> node : config.nodes()) {
        }
        return null;
    }

    @NotNull
    private static Config transform(Class<?> clazz) {
        Config config = Config.config();
        if (!clazz.isAnnotationPresent(me.combimagnetron.lagoon.config.annotation.Config.class)) {
            return config;
        }
        Set<Field> fields = Arrays.stream(clazz.getFields()).filter(field -> !field.isAnnotationPresent(Excluded.class)).collect(Collectors.toSet());
        for (Field field : fields) {
            final ProcessedField<?> processedField = ProcessedField.from(field);
            if (field.isAnnotationPresent(Optional.class)) {
                config.node(Node.optional(processedField.name(), processedField.type()));
            } else if (field.isAnnotationPresent(Required.class)) {
                config.node(Node.required(processedField.name(), processedField.type()));
            } else if (field.isAnnotationPresent(Anonymous.class)) {
                config.node(Node.anonymous(processedField.type()));
            }

        }
        return config;
    }

    record ProcessedField<T>(String name, Class<T> type) {

        public static <V> ProcessedField<V> from(Field field) {
            String name = field.isAnnotationPresent(Name.class) ? field.getAnnotation(Name.class).name() : field.getName();
            Class<V> type = (Class<V>) field.getType();
            return new ProcessedField<>(name, type);
        }


    }





}
