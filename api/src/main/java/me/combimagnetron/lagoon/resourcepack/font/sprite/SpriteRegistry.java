package me.combimagnetron.lagoon.resourcepack.font.sprite;

import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.data.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SpriteRegistry {
    private static final Map<Identifier, Sprite> SPRITES = new HashMap<>();
    private static final Map<Identifier, SpriteSheet> SPRITE_SHEETS = new HashMap<>();

    public static Operation<Void> registerSprite(Identifier identifier, Sprite sprite) {
        return Operation.simple(() -> {
            if (sprite.heightAndAscent().pairNull())
                return;
            SPRITES.put(identifier, sprite);
        });
    }

    public static Operation<Void> registerSheet(Identifier identifier, SpriteSheet spriteSheet) {
        return Operation.simple(() -> {
            SPRITE_SHEETS.put(identifier, spriteSheet);
            SPRITES.putAll(spriteSheet.sprites());
        });
    }


}
