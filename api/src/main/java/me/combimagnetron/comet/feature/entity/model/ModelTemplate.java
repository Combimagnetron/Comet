package me.combimagnetron.comet.feature.entity.model;

import me.combimagnetron.comet.data.Identifier;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class ModelTemplate {

    private final Identifier identifier;

    public ModelTemplate(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier identifier() {
        return identifier;
    }



}
