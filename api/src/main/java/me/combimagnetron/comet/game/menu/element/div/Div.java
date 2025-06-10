package me.combimagnetron.comet.game.menu.element.div;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.Editable;
import me.combimagnetron.comet.game.menu.Pos2D;
import me.combimagnetron.comet.game.menu.element.Element;
import me.combimagnetron.comet.game.menu.element.Position;
import me.combimagnetron.comet.image.Canvas;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface Div extends Editable {

    Identifier identifier();

    Canvas canvas();

    Pos2D size();

    Position position();

    Div position(Position pos);

    Div add(Element element);

    Div remove(Identifier identifier);

    Div remove(Element element);

    Div hide(Identifier identifier);

    Div hide(Element element);

    Div show(Identifier identifier);

    Div show(Element element);

    Collection<Element> elements();

    Canvas render(Canvas image);

    static Div div(Identifier identifier) {
        return new Impl(identifier);
    }

    final class Impl implements Div {
        private final HashMap<Identifier, Element> elements = new HashMap<>();
        private final HashSet<Element> hidden = new HashSet<>();
        private final Identifier identifier;
        private Canvas canvas = Canvas.image(Pos2D.of(256, 256));
        private Position pos = Position.pixel(0, 0);

        public Canvas render(Canvas image) {
            for (Element element : elements.values()) {
                if (!hidden.contains(element)) {
                    image = image.place(element.canvas(), Pos2D.of(element.position().x(), element.position().y()));
                }
            }
            return image;
        }

        Impl(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }

        @Override
        public Canvas canvas() {
            return canvas;
        }

        @Override
        public Pos2D size() {
            return canvas.size();
        }

        @Override
        public Position position() {
            return pos;
        }

        @Override
        public Div position(Position pos) {
            this.pos = pos;
            return this;
        }

        @Override
        public Div add(Element element) {
            elements.put(element.identifier(), element);
            return this;
        }

        @Override
        public Div remove(Identifier identifier) {
            elements.remove(identifier);
            return this;
        }

        @Override
        public Div remove(Element element) {
            elements.remove(element.identifier());
            return this;
        }

        @Override
        public Div hide(Identifier identifier) {
            hidden.add(elements.get(identifier));
            return this;
        }

        @Override
        public Div hide(Element element) {
            hidden.add(element);
            return this;
        }

        @Override
        public Div show(Identifier identifier) {
            hidden.remove(elements.get(identifier));
            return this;
        }

        @Override
        public Div show(Element element) {
            hidden.remove(element);
            return this;
        }

        @Override
        public Collection<Element> elements() {
            return elements.values();
        }
    }

}