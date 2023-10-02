package me.combimagnetron.halo.transformer;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.service.Deployment;
import me.combimagnetron.comet.util.Values;

import java.util.Objects;
import java.util.UUID;

public interface Transformer<T> {
    Transformer<UUID> UUID = of("Uuid", java.util.UUID.class, ByteBuffer.Adapter.UUID);
    Transformer<String> STRING = of("String", String.class, ByteBuffer.Adapter.STRING);
    Transformer<Integer> INT = of("Int", Integer.class, ByteBuffer.Adapter.INT);
    Transformer<Double> DOUBLE = of("Double", Double.class, ByteBuffer.Adapter.DOUBLE);
    Transformer<Identifier> IDENTIFIER = of("Identifier", Identifier.class, ByteBuffer.Adapter.IDENTIFIER);
    Transformer<Deployment> DEPLOYMENT = of("Deployment", Deployment.class, ByteBuffer.Adapter.DEPLOYMENT);
    Values<Transformer<?>> VALUES = Values.of(UUID, STRING, INT, DOUBLE, IDENTIFIER, DEPLOYMENT);

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
