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

    String packageName();

    Collection<VariableParameter> parameters();

    static SatelliteField message(String name, String variableName, VariableParameter... parameters) {
        return new MessageField(name, variableName, parameters);
    }

    static SatelliteField type(String name, String variableName, VariableParameter... parameters) {
        return new TypeField(name, variableName, parameters);
    }

    static SatelliteField field(TokenizedResult result, String packageName) {
        boolean repeated = result.ordered().next().token().captured().startsWith("repeated");
        String name = result.content(Token.Type.OBJECT);
        String type = result.content(Token.Type.KEYWORD);
        if (Objects.equals(type, "type")) {
            RegisteredType.add(RegisteredType.of(name, TypeField.Dummy.class, ByteBuffer.Adapter.BYTE_ARRAY));
        }
        VariableParameter[] parameters = result.all(Token.Type.KEYWORD_OBJECT_PAIR).stream().map(pair -> {
            String[] split = pair.token().captured().split(" ");
            return new VariableParameter(split[1], RegisteredType.VALUES.stream().filter(registeredType -> registeredType.name().equals(split[0])).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown type: " + split[0])));
        }).toArray(VariableParameter[]::new);
        if (Objects.equals(type, "message")) {
            return new MessageField(name, packageName, parameters);
        } else if (Objects.equals(type, "type")) {
            return new TypeField(name, packageName, parameters);
        } else if (Objects.equals(type, "method")) {
            System.out.println(result.content(Token.Type.of("( : \\w+[\\<\\w\\>]+)")));
            String typeName = result.content(Token.Type.of("( : \\w+[\\<\\w\\>]+)")).contains(":") ? result.content(Token.Type.of("( : \\w+[\\<\\w\\>]+)")).replace(" : ", "") : "Void";
            System.out.println(typeName);
            return new MethodField(name, packageName, RegisteredType.find(typeName), parameters);
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    final class MessageField implements SatelliteField {
        private final String name;
        private final String packageName;
        private final List<VariableParameter> parameters;
        private int id;

        public MessageField(String name, String packageName, VariableParameter... parameters) {
            this.name = name;
            this.packageName = packageName;
            this.parameters = Arrays.stream(parameters).toList();
            Satellite.registry().save(this);
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public String packageName() {
            return packageName;
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

    record TypeField(String name, String packageName, VariableParameter... parametersArray) implements SatelliteField {

        @Override
        public Collection<VariableParameter> parameters() {
            return Arrays.stream(parametersArray).toList();
        }

        interface Dummy {

        }

    }

    record MethodField(String name, String packageName, RegisteredType<?> type, VariableParameter... parametersArray) implements SatelliteField {

        @Override
        public Collection<VariableParameter> parameters() {
            return Arrays.stream(parametersArray).toList();
        }

        public MessageField message() {
            return new MessageField(name + "Message", packageName, parametersArray);
        }

    }

    record VariableParameter(String name, RegisteredType<?> type) {
    }

}
