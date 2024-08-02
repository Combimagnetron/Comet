package me.combimagnetron.halo.compiler;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.*;
import me.combimagnetron.halo.transformer.Transformer;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.util.Values;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

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
        LoggerFactory.getLogger(Compiler.class).info("test");
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
            System.out.println(path.toFile().getName());
            if (file == null) {
                throw new RuntimeException("Halo class " + path.toFile().getName() + " could not be read. Please look for syntax errors.");
            }
            ProtocolDirection direction = ProtocolDirection.find(file.split("-")[0]);
            String name = Token.Type.OBJECT.pattern().matcher(file).results().findFirst().orElseThrow().group();
            Matcher matcher = Token.Type.METHOD_REFERENCE.pattern().matcher(file);
            List<Field> fields1 = new LinkedList<>();
            matcher.results().peek(matchResult -> {
                String[] strings = matchResult.group().split(" ");
                Transformer<?> type = Transformer.find(strings[0]);
                String varName = strings[1];
                fields1.add(new Field(type.clazz(), varName, type));
            });
            matcher.results().forEach(result -> {
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
            TypeSpec.Builder builder = TypeSpec.recordBuilder(name + "Message").addModifiers(Modifier.PUBLIC).addSuperinterface(Message.class);
            List<ParameterSpec> specs = fields.stream().map(field -> ParameterSpec.builder(field.type, field.varName).build()).toList();
            specs.stream().forEachOrdered(builder::addRecordComponent);
            final StringBuilder builder1 = new StringBuilder();
            Class<?>[] classes = fields.stream().map(field -> field.type).distinct().toArray(Class[]::new);
            boolean first = true;
            for (Field field : fields) {
                if (first) {
                    builder1.append(field.varName);
                    first = false;
                    continue;
                }
                builder1.append(", " + field.varName);
            }
            ClassName className = ClassName.get("me.combimagnetron.generated", name + "Message");
            MethodSpec.Builder statics = MethodSpec.methodBuilder("of")
                    .addParameters(specs)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addCode("return new $T(" + builder1 + ");", className)
                    .returns(className);
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
            fields.forEach(field -> codeBuilder.add("\nbuffer.write($T.$L, $L);", ByteBuffer.Adapter.class, field.transformer.adapterName(), field.varName));
            methodSpecBuilder.addCode(codeBuilder.build());
            builder.addMethod(
                    methodSpecBuilder.build()
            ).addMethod(statics.build()).addMethod(buffer.build()).addMethod(id.build());
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
