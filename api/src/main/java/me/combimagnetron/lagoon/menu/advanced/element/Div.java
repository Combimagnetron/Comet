package me.combimagnetron.lagoon.menu.advanced.element;

import me.combimagnetron.lagoon.menu.advanced.Positionable;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.util.Identifier;
import me.combimagnetron.lagoon.util.Pos2D;

import java.util.*;
import java.util.function.Predicate;

public interface Div extends Positionable {

    Operation<Collection<Element>> toCollection();

    Operation<Div> filter(Predicate<? super Map.Entry<Identifier, Element>> predicate);

    <T extends Element> Operation<Void> add(Identifier identifier, T t);

    Operation<Void> remove(Identifier identifier);

    static Div create() {
        return new SimpleDiv();
    }

    class SimpleDiv implements Div {
        private final TreeMap<Identifier, Element> treeMap = new TreeMap<>();
        private Pos2D pos;

        SimpleDiv() {}

        private SimpleDiv(Map<Identifier, Element> map) {
            this.treeMap.putAll(map);
        }

        @Override
        public Operation<Collection<Element>> toCollection() {
            return Operation.executable(treeMap::values);
        }

        @Override
        public Operation<Div> filter(Predicate<? super Map.Entry<Identifier, Element>> predicate) {
            return Operation.executable(() -> {
                LinkedHashMap<Identifier, Element> elementLinkedHashMap = new LinkedHashMap<>();
                treeMap.entrySet().stream().filter(predicate).forEachOrdered(entry -> elementLinkedHashMap.put(entry.getKey(), entry.getValue()));
                return new SimpleDiv(elementLinkedHashMap);
            });
        }

        @Override
        public <T extends Element> Operation<Void> add(Identifier identifier, T t) {
            return Operation.simple(() -> treeMap.put(identifier, t));
        }

        @Override
        public Operation<Void> remove(Identifier identifier) {
            return Operation.simple(() -> treeMap.remove(identifier));
        }

        @Override
        public Operation<Void> pos(Pos2D pos) {
            return Operation.simple(() -> this.pos = pos);
        }

        @Override
        public Pos2D pos() {
            return pos;
        }
    }

}
