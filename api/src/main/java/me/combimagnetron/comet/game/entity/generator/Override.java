package me.combimagnetron.comet.game.entity.generator;

import java.util.HashMap;
import java.util.Map;

public class Override {
    private final Map<String, Integer> predicate = new HashMap<>();
    private final String name;

    public Override(String modelName, String boneName, int modelData) {
        this.name = "comet:%s/%s".formatted(modelName, boneName);
        predicate.put("custom_model_data", modelData);
    }

    public Map<String, Integer> predicate() {
        return predicate;
    }

    public String model() {
        return name;
    }

}
