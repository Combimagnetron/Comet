package me.combimagnetron.lagoon.service.config;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public interface Parameter<V, T> {
    V first();

    T second();

    void first(V v);

    void second(T t);

    @Nullable Path location();



}
