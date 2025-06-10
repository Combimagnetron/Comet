package me.combimagnetron.comet.game.menu.draft;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.Editable;
import me.combimagnetron.comet.game.menu.element.Element;
import me.combimagnetron.comet.game.menu.element.div.Div;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public record Edit<T>(Identifier identifier, List<Function<T, T>> edits, Class<T> type) {

        public static EditBuilder<Div, Draft.DivSubSection> div() {
            EditBuilder<Div, Draft.DivSubSection> builder = new EditBuilder<>(Div.class);
            builder.supplier(() -> new Draft.DivSubSection(builder));
            return builder;
        }

        public static EditBuilder<Element, Draft.ElementSubSection> element() {
            EditBuilder<Element, Draft.ElementSubSection> builder = new EditBuilder<>(Element.class);
            builder.supplier(() -> new Draft.ElementSubSection(builder));
            return builder;
        }

        public static class EditBuilder<T extends Editable, V extends Draft.SubSection<T>> {
            private Identifier identifier;
            private Supplier<V> supplier;
            private final Class<T> type;

            public EditBuilder(Class<T> type) {
                this.type = type;
            }

            void supplier(Supplier<V> supplier) {
                this.supplier = supplier;
            }

            public V section() {
                return supplier.get();
            }

            public EditBuilder<T, V> identifier(Identifier identifier) {
                this.identifier = identifier;
                return this;
            }

            public Edit<T> done() {
                return new Edit<>(identifier, section().product(), type);
            }

        }

    }