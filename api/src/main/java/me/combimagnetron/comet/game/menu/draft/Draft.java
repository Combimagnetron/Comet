package me.combimagnetron.comet.game.menu.draft;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.Editable;
import me.combimagnetron.comet.game.menu.Menu;
import me.combimagnetron.comet.game.menu.Pos2D;
import me.combimagnetron.comet.game.menu.element.Element;
import me.combimagnetron.comet.game.menu.element.Position;
import me.combimagnetron.comet.game.menu.element.div.Div;
import me.combimagnetron.comet.game.menu.element.impl.TextElement;
import me.combimagnetron.comet.game.menu.style.ColorStyle;
import me.combimagnetron.comet.game.menu.style.Style;
import me.combimagnetron.comet.game.menu.style.Text;
import me.combimagnetron.comet.image.Color;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Draft {

    Draft element(Element element, Position position);

    Draft div(Div div, Position position);

    Draft edit(Edit<?> edit);

    final class ElementSubSection implements SubSection<Element> {
        private final List<Function<Element, Element>> element = new ArrayList<>();
        private final Edit.EditBuilder<Element, ElementSubSection> editBuilder;

        ElementSubSection(Edit.EditBuilder<Element, ElementSubSection> element) {
            this.editBuilder = element;
        }

        public ElementSubSection position(Position pos) {
            element.add(d -> d.position(pos));
            return this;
        }

        public <T> ElementSubSection style(Style<T> style, Position position, T t) {
            element.add(d -> d.style(style, position, t));
            return this;
        }

        public <T> ElementSubSection style(Style<T> style, T t) {
            element.add(d -> d.style(style, t));
            return this;
        }

        @Override
        public List<Function<Element, Element>> product() {
            return element;
        }

        @Override
        public Edit.EditBuilder<Element, ElementSubSection> back() {
            return editBuilder;
        }

    }

    final class DivSubSection implements SubSection<Div> {
        private final List<Function<Div, Div>> div = new ArrayList<>();
        private final Edit.EditBuilder<Div, DivSubSection> editBuilder;

        DivSubSection(Edit.EditBuilder<Div, DivSubSection> builder) {
            this.editBuilder = builder;
        }

        public DivSubSection position(Position pos) {
            div.add(d -> d.position(pos));
            return this;
        }

        public DivSubSection add(Element element) {
            div.add(d -> d.add(element));
            return this;
        }

        public DivSubSection remove(Identifier identifier) {
            div.add(d -> d.remove(identifier));
            return this;
        }

        public DivSubSection remove(Element element) {
            div.add(d -> d.remove(element));
            return this;
        }

        public DivSubSection hide(Identifier identifier) {
            div.add(d -> d.hide(identifier));
            return this;
        }

        public DivSubSection hide(Element element) {
            div.add(d -> d.hide(element));
            return this;
        }

        public DivSubSection show(Identifier identifier) {
            div.add(d -> d.show(identifier));
            return this;
        }

        public DivSubSection show(Element element) {
            div.add(d -> d.show(element));
            return this;
        }

        @Override
        public List<Function<Div, Div>> product() {
            return div;
        }

        @Override
        public Edit.EditBuilder<Div, DivSubSection> back() {
            return editBuilder;
        }


    }

    static Draft draft() {
        return new Impl();
    }

    class Impl implements Draft {
        private final Set<Edit<?>> edits = new HashSet<>();

        @Override
        public Draft element(Element element, Position position) {
            return this;
        }

        @Override
        public Draft div(Div div, Position position) {
            return this;
        }

        public Set<Edit<?>> edits() {
            return edits;
        }

        @Override
        public Draft edit(Edit<?> edit) {
            edits.add(edit);
            return this;
        }
    }

    interface SubSection<T extends Editable> {

        List<Function<T, T>> product();

        <V extends SubSection<T>> Edit.EditBuilder<T, V> back();

    }

}
