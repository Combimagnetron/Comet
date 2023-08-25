package me.combimagnetron.halo.transformer;

import me.combimagnetron.lagoon.internal.entity.metadata.type.VarInt;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import me.combimagnetron.lagoon.util.Values;

import java.util.Objects;
import java.util.UUID;

public interface Transformer<T> {
    Transformer<UUID> UUID = of("Uuid", java.util.UUID.class, ByteBuffer.Adapter.UUID);
    Transformer<String> STRING = of("String", String.class, ByteBuffer.Adapter.STRING);
    Transformer<Integer> INT = of("Int", Integer.class, ByteBuffer.Adapter.INT);
    Transformer<Double> DOUBLE = of("Double", Double.class, ByteBuffer.Adapter.DOUBLE);
    Values<Transformer<?>> VALUES = Values.of(UUID, STRING, INT, DOUBLE);

    String identifier();

    Class<T> clazz();

    ByteBuffer.Adapter<?> adapter();

    static <T> Transformer<T> of(String identifier, Class<T> clazz, ByteBuffer.Adapter<?> adapter) {
        return new Entry<>(identifier, clazz, adapter);
    }

    static <T> Transformer<T> find(String string) {
        return (Transformer<T>) VALUES.values().stream().filter(transformer -> Objects.equals(transformer.identifier(), string)).findAny().orElseThrow();
    }

    record Entry<T>(String identifier, Class<T> clazz, ByteBuffer.Adapter<?> adapter) implements Transformer<T> {

    }

}
