package me.combimagnetron.comet.feature.menu.draft;

import me.combimagnetron.comet.config.Config;
import me.combimagnetron.comet.config.element.Node;
import me.combimagnetron.comet.config.element.Section;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.data.database.Credentials;
import me.combimagnetron.comet.feature.menu.element.Element;
import me.combimagnetron.comet.feature.menu.element.Position;
import me.combimagnetron.comet.feature.menu.element.div.Div;
import me.combimagnetron.comet.feature.menu.element.impl.ButtonElement;
import me.combimagnetron.comet.feature.menu.element.impl.TextElement;
import me.combimagnetron.comet.feature.menu.style.Text;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public interface Draft {

    Draft element(Element element, Position position);

    Draft div(Div div, Position position);

    <T> Draft edit(Identifier identifier, Class<T> type, Function<T, T> function);

    record Edit<T>(Identifier identifier, Class<T> type, Function<T, T> function) {

    }

    enum EditType {
        ADD, REPLACE, REMOVE, RENAME
    }

    final class ElementSubSection implements SubSection<Element> {
        private final Element element;
        private final Draft draft;

        ElementSubSection(Element element, Draft draft) {
            this.element = element;
            this.draft = draft;
        }

        @Override
        public Element product() {
            return element;
        }

        @Override
        public Draft done() {
            return draft;
        }
    }

    static Draft draft() {
        return new Impl();
    }

    static void test() {

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

        @Override
        public <T> Draft edit(Identifier identifier, Class<T> type, Function<T, T> function) {
            return this;
        }
    }

    interface SubSection<T> {

        T product();

        Draft done();

    }

}
