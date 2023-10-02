package me.combimagnetron.comet.feature.entity.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {
    private final transient int modelData;
    private final List<Cube> elements = new ArrayList<>();
    private final Map<Integer, String> textures = new HashMap<>();
    private final Map<String, Display> display = new HashMap<>();

    public Item(int modelData) {
        display.put("head", new Display());
        this.modelData = modelData;
    }

    public int modelData() {
        return modelData;
    }

    public static Item of(int modelData) {
        return new Item(modelData);
    }

    public List<Cube> elements() {
        return elements;
    }

    public void texture(int i, String string) {
        this.textures.put(i, string);
    }

    public void display(String string, Display display) {
        this.display.put(string, display);
    }

    public void element(Cube element) {
        this.elements.add(element);
    }
    public Map<Integer, String> textures() {
        return textures;
    }

    public Map<String, Display> displays() {
        return display;
    }
}
