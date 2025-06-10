package me.combimagnetron.comet.game.menu.element.impl;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.Pos2D;
import me.combimagnetron.comet.game.menu.element.Position;
import me.combimagnetron.comet.game.menu.element.SimpleBufferedElement;
import me.combimagnetron.comet.game.menu.style.Text;
import me.combimagnetron.comet.image.Canvas;

public class TextElement extends SimpleBufferedElement<TextElement> {
    private final Text text;

    public static TextElement textElement(Position position, Identifier identifier, Text text) {
        return new TextElement(identifier, position, Pos2D.of(256, 256), text);
    }

    TextElement(Identifier identifier, Position position, Pos2D size, Text text) {
        super(size, identifier, position);
        this.text = text;

    }

    public Text text() {
        return text;
    }

    @Override
    protected Canvas render(Canvas image) {
        return null;
    }
}
