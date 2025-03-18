package me.combimagnetron.comet.game.menu.element.impl;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.element.SimpleBufferedElement;
import me.combimagnetron.comet.image.Canvas;
import me.combimagnetron.comet.game.menu.Pos2D;
import me.combimagnetron.comet.game.menu.element.Position;

import java.util.LinkedList;

public class SelectorElement extends SimpleBufferedElement<SelectorElement> {
    private final LinkedList<ButtonElement> buttons = new LinkedList<>();
    private Canvas canvas;
    private Pos2D size;

    protected SelectorElement(Pos2D size, Identifier identifier, Position position) {
        super(size, identifier, position);
        render();
    }

    @Override
    protected Canvas render(Canvas image) {
        return null;
    }

    protected void render() {
        int x = buttons.getFirst().size().xi();
        int y = buttons.size() * 10 + (buttons.size() + 1) * 2 + 2;
        Canvas canvas = Canvas.image(Pos2D.of(x, y));
        int yCoord = 1;
        for (ButtonElement button : buttons) {
            yCoord += 11;
            canvas = canvas.place(button.canvas(), Pos2D.of(2, yCoord));
        }
        this.canvas = canvas;
    }

    public static Builder selectorElement(Pos2D pos2D, Identifier identifier, Position position) {
        return new Builder(pos2D, identifier, position);
    }

    @Override
    public Identifier identifier() {
        return null;
    }

    @Override
    public Position position() {
        return null;
    }


    public static class Builder {
        private final LinkedList<ButtonElement> buttons = new LinkedList<>();
        private final Pos2D size;
        private final Identifier identifier;
        private final Position position;

        public Builder(Pos2D size, Identifier identifier, Position position) {
            this.size = size;
            this.identifier = identifier;
            this.position = position;
        }

        public Builder button(ButtonElement button) {
            buttons.add(button);
            return this;
        }

        public SelectorElement build() {
            return new SelectorElement(size, identifier, position);
        }
    }

}
