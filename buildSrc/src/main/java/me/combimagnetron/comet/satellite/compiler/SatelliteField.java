package me.combimagnetron.comet.satellite.compiler;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.satellite.Satellite;
import me.combimagnetron.comet.satellite.matcher.Token;
import me.combimagnetron.comet.satellite.matcher.TokenizedResult;
import org.cojen.maker.ClassMaker;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public interface SatelliteField {

    String name();

    Collection<VariableParameter> parameters();

    static SatelliteField message(String name, VariableParameter... parameters) {
        return new MessageField(name, parameters);
    }

    static SatelliteField type(String name, VariableParameter... parameters) {
        return new TypeField(name, parameters);
    }

    static SatelliteField field(TokenizedResult result) {
        boolean repeated = result.ordered().next().token().captured().startsWith("repeated");
        String name = result.content(Token.Type.OBJECT);
        String type = result.content(Token.Type.KEYWORD);
        if (Objects.equals(type, "type")) {
            RegisteredType.add(RegisteredType.of(name, TypeField.Dummy.class, ByteBuffer.Adapter.BYTE_ARRAY));
        }
        VariableParameter[] parameters = result.all(Token.Type.KEYWORD_OBJECT_PAIR).stream().map(pair -> {
            String[] split = pair.token().captured().split(" ");
            return new VariableParameter(split[1], RegisteredType.VALUES.stream().filter(registeredType -> registeredType.name().equals(split[0])).findFirst().orElseThrow());
        }).toArray(VariableParameter[]::new);
        if (Objects.equals(type, "message")) {
            return new MessageField(name, parameters);
        } else if (Objects.equals(type, "type")) {
            return new TypeField(name, parameters);
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    final class MessageField implements SatelliteField {
        private final String name;
        private final List<VariableParameter> parameters;
        private int id;

        public MessageField(String name, VariableParameter... parameters) {
            this.name = name;
            this.parameters = Arrays.stream(parameters).toList();
            Satellite.registry().save(this);
        }

        @Override
        public String name() {
            return name;
        }

        public Collection<VariableParameter> parameters() {
            return parameters;
        }

        public int id() {
            return id;
        }

        public int id(int id) {
            return this.id = id;
        }

    }

    record TypeField(String name, VariableParameter... parametersArray) implements SatelliteField {

        @Override
        public Collection<VariableParameter> parameters() {
            return Arrays.stream(parametersArray).toList();
        }

        interface Dummy {

        }

    }

    record VariableParameter(String name, RegisteredType<?> type) {
    }

}
