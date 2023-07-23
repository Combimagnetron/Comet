package me.combimagnetron.lagoon.condition;

import java.util.function.Function;

public record Supplier<T>(T value) {
    public static <T> Supplier<T> of(T value) {
        return new Supplier<>(value);
    }
}
