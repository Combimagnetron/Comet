package me.combimagnetron.lagoon.service.config;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public class StringStringParameter implements Parameter<String, String> {
    private String first, second;

    @Override
    public String first() {
        return first;
    }

    @Override
    public String second() {
        return second;
    }

    @Override
    public void first(String s) {
        this.first = s;
    }

    @Override
    public void second(String s) {
        this.second = s;
    }

    @Override
    public @Nullable Path location() {
        return null;
    }
}
