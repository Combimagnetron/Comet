package me.combimagnetron.halo.transformer;

import java.util.UUID;

public interface Transformer<T> {
    Transformer<UUID> UUID = of("Uuid", java.util.UUID.class);
    Transformer<String> STRING = of("String", String.class);
    Transformer<Integer> INT = of("Int", Integer.class);
    Transformer<Double> DOUBLE = of("Double", Double.class);

    static <T> Transformer<T> of(String identifier, Class<T> clazz) {
        return new Entry<>(identifier, clazz);
    }

    record Entry<T>(String identifier, Class<T> clazz) implements Transformer<T> {



    }

}
