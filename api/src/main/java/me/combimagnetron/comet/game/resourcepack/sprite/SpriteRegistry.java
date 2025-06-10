package me.combimagnetron.comet.game.resourcepack.sprite;

import me.combimagnetron.comet.data.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SpriteRegistry {
    private static final Map<Identifier, Sprite> SPRITES = new HashMap<>();
    private static final Map<Identifier, SpriteSheet> SPRITE_SHEETS = new HashMap<>();

    public static void register(Identifier identifier, Sprite sprite) {

        SPRITES.put(identifier, sprite);
    }

    public static void register(Identifier identifier, SpriteSheet spriteSheet) {
        SPRITE_SHEETS.put(identifier, spriteSheet);
        SPRITES.putAll(spriteSheet.sprites());
    }


}
