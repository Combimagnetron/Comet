package me.combimagnetron.comet.game.menu.element;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.Pos2D;

import me.combimagnetron.comet.game.menu.style.Style;
import me.combimagnetron.comet.image.Canvas;

public abstract class SimpleBufferedElement<V extends Element> implements Element {
    private Canvas image;
    private final Pos2D size;
    private final Identifier identifier;
    private Position position;

    public SimpleBufferedElement(Pos2D size, Identifier identifier, Position position) {
        this.size = size;
        this.image = Canvas.image(size);
        this.identifier = identifier;
        this.position = position;
    }

    protected abstract Canvas render(Canvas image);

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public Canvas canvas() {
        return render(image);
    }

    @Override
    public V position(Position pos) {
        this.position = pos;
        return (V) this;
    }

    @Override
    public Position position() {
        return position;
    }

    @Override
    public <T> V style(Style<T> style, Position pos2D, T t) {
        this.image = style.edit(this.image, pos2D, t);
        return (V) this;
    }

    @Override
    public <T> V style(Style<T> style, T t) {
        return style(style, Position.pixel(0, 0), t);
    }


    public Pos2D size() {
        return size;
    }

}
