package me.combimagnetron.comet.game.entity.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTemplate {
    private final List<Override> overrides = new ArrayList<>();
    private final Map<String,String> textures = new HashMap<>();;
    private final String parent;


    public ItemTemplate() {
        this.parent = "minecraft:item/generated";
        this.textures.put("layer0","minecraft:item/leather_horse_armor");
    }

    public List<Override> getOverrides() {
        return overrides;
    }

}
