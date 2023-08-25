package me.combimagnetron.halo.compiler;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.*;
import me.combimagnetron.halo.transformer.Transformer;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import me.combimagnetron.lagoon.util.Values;
import org.jetbrains.annotations.Nullable;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

public class Compiler {

    public static void main(String[] args) throws IOException {
        Path path = Path.of(args[0]);
        if (path.toFile().isDirectory()) {
            File[] files = path.toFile().listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                JavaFile javaFile = compile(Path.of(file.toURI()));
                javaFile.writeTo(Path.of(args[1]));
            }
        }
    }

    private static JavaFile compile(Path path) {
        HaloClass haloClass = HaloClass.haloClass(path);
        return haloClass.javaFile();
    }

    public interface ProtocolDirection {
        ProtocolDirection SERVER_BOUND = Impl.of("server");
        ProtocolDirection PROXY_BOUND = Impl.of("proxy");
        ProtocolDirection SERVICE_BOUND = Impl.of("service");
        Values<ProtocolDirection> VALUES = Values.of(SERVER_BOUND, PROXY_BOUND, SERVICE_BOUND);

        String direction();

        int next();

        static ProtocolDirection find(String string) {
            return VALUES.values().stream().filter(protocolDirection -> string.split("-")[0].equals(protocolDirection.direction())).findAny().orElseThrow();
        }

        final class Impl implements ProtocolDirection {
            private final AtomicInteger id = new AtomicInteger(0);
            private final String direction;

            public Impl(String direction) {
                this.direction = direction;
            }

            public static Impl of(String direction) {
                return new Impl(direction);
            }

            @Override
            public int next() {
                return id.getAndIncrement();
            }

            @Override
            public String direction() {
                return direction;
            }
        }
    }

    public record HaloClass(String name, ProtocolDirection protocolDirection, Collection<Field> fields, @Nullable Type[] types) {

        public static HaloClass haloClass(Path path) {
            List<String> lines;
            try {
                lines = Files.readAllLines(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            StringBuilder builder = new StringBuilder();
            lines.forEach(builder::append);
            String all = builder.toString();
            String file = find(Token.Type.CLASS, all, 1);
            ProtocolDirection direction = ProtocolDirection.find(file.split("-")[0]);
            String name = Token.Type.OBJECT.pattern().matcher(file).results().findFirst().orElseThrow().group();
            Matcher matcher = Token.Type.METHOD_REFERENCE.pattern().matcher(file);
            List<Field> fields1 = new LinkedList<>();
            System.out.println(matcher.results().peek(matchResult -> {
                System.out.println("aa");
                String[] strings = matchResult.group().split(" ");
                Transformer<?> type = Transformer.find(strings[0]);
                String varName = strings[1];
                fields1.add(new Field(type.clazz(), varName, type));
                System.out.println(matchResult);
            }).count());
            matcher.results().forEach(result -> {
                System.out.println("aa");
                String[] strings = result.group().split(" ");
                Transformer<?> type = Transformer.find(strings[0]);
                String varName = strings[1];
                varName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, varName);
                fields1.add(new Field(type.clazz(), varName, type));
            });
            List<Field> fieldList = matcher.results().map(result -> {
                String[] strings = result.group().split(" ");
                Transformer<?> type = Transformer.find(strings[0]);
                String varName = strings[1];
                return new Field(type.clazz(), varName, type);
            }).toList();
            System.out.println(fields1.size());
            return new HaloClass(name, direction, fields1, null);
        }

        private static String find(Token.Type type, String string, int match) {
            Matcher matcher = type.pattern().matcher(string);
            String out = null;
            if (matcher.matches()) {
                out = matcher.group(match);
            }
            return out;
        }

        public JavaFile javaFile() {
            TypeSpec.Builder builder = TypeSpec.recordBuilder(name + "Message").addSuperinterface(Message.class);
            fields.stream().map(field -> ParameterSpec.builder(field.type, field.varName).build()).forEachOrdered(builder::addRecordComponent);
            MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("write")
                    .addModifiers(Modifier.PUBLIC)
                    .addCode("final $T buffer = buffer();", ByteBuffer.class)
                    .addAnnotation(Override.class);
            MethodSpec.Builder buffer = MethodSpec.methodBuilder("buffer")
                    .returns(ByteBuffer.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addCode("return $T.empty();", ByteBuffer.class);
            MethodSpec.Builder id = MethodSpec.methodBuilder("id")
                    .returns(int.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addCode("return $L;", protocolDirection.next());
            CodeBlock.Builder codeBuilder = CodeBlock.builder();
            fields.forEach(field -> codeBuilder.add("\nbuffer.write($T.$L, $L);", ByteBuffer.Adapter.class, field.transformer.identifier().toUpperCase(), field.varName));
            methodSpecBuilder.addCode(codeBuilder.build());
            builder.addMethod(
                    methodSpecBuilder.build()
            ).addMethod(buffer.build()).addMethod(id.build());
            JavaFile javaFile = JavaFile.builder("me.combimagnetron.generated",
                    builder.build()
            ).indent("    ").build();
            return javaFile;
        }


        public record Field(Class<?> type, String varName, Transformer<?> transformer) {

        }

        public interface Type {

            record EnumType(String... values) implements Type {

            }

            record ClassType(Field... fields) implements Type {

            }


        }


    }


}
