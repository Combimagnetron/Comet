package me.combimagnetron.lagoon.service.config;

import org.jetbrains.annotations.Nullable;

import java.io.File;

public abstract class Parameter<V, T> {
    abstract V first();

    abstract T second();

    abstract V first(V v);

    abstract T second(T t);

    abstract @Nullable File location();



}
