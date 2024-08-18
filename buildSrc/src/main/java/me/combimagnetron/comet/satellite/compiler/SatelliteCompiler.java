package me.combimagnetron.comet.satellite.compiler;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.satellite.Satellite;
import me.combimagnetron.comet.satellite.SatelliteIdRegistry;
import me.combimagnetron.comet.satellite.matcher.*;
import org.cojen.maker.ClassMaker;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SatelliteCompiler {

    public SatelliteClass transform(Path path) {
        String content = read(path);
        TokenizedResult clazz = TokenMatcher.matcher(content)
                .section(
                        MatcherSection.section()
                                .token(MatcherToken.required(Token.Type.CLASS))
                )
                .validate();
        TokenizedResult tokenizedClass = clazz.ordered().next().matcher()
                .section(
                        MatcherSection.section()
                                .token(MatcherToken.required(Token.Type.KEYWORD))
                                .token(MatcherToken.required(Token.Type.OBJECT))
                                .token(MatcherToken.required(Token.Type.KEYWORD))
                                .token(MatcherToken.required(Token.Type.OBJECT))
                                .token(MatcherToken.optional(Token.Type.KEYWORD))
                                .token(MatcherToken.optional(Token.Type.OBJECT))
                                .token(MatcherToken.multiple(Token.Type.METHOD))
                )
                .validate();
        Identifier identifier = Identifier.of(tokenizedClass.content(1), tokenizedClass.content(3));
        Collection<SatelliteField> fields = new LinkedHashSet<>();
        tokenizedClass.all(Token.Type.METHOD).stream().sorted((res, res2) -> Pattern.compile("type [A-Z][a-z]\\w+").matcher(res.token().captured()).matches() ? 1 : -1).forEach(result -> {
            TokenizedResult method = result.matcher()
                    .section(
                            MatcherSection.section()
                                    .token(MatcherToken.required(Token.Type.of("responseless")))
                                    .token(MatcherToken.optional(Token.Type.KEYWORD))
                                    .token(MatcherToken.required(Token.Type.OBJECT))
                                    .token(MatcherToken.multiple(Token.Type.KEYWORD_OBJECT_PAIR))
                    )
                    .validate();
            fields.add(SatelliteField.field(method));
        });
        SatelliteDependency dependency = SatelliteDependencyResolver.resolve(tokenizedClass);
        /* TODO
            move to api package and rename satellite to lexer/parser so it can be used for future other scripting languages/config files.
            generate java service class
            fix id registry
            implement responseless and repeated keywords
         */
        return SatelliteClass.of(identifier, dependency, fields);
    }

    public void compile(SatelliteClass satelliteClass, Path path) {
        for (SatelliteField field : satelliteClass.fields()) {
            CompilationUnit compilationUnit = null;
            if (field instanceof SatelliteField.MessageField messageField) {
                compilationUnit = generateMessageClass(messageField);
            } else if (field instanceof SatelliteField.TypeField typeField) {
                compilationUnit = generateTypeClass(typeField);
            }
            path.resolve(satelliteClass.identifier().key().string().toLowerCase()).toFile().mkdirs();
            try (FileWriter writer = new FileWriter(path.resolve(satelliteClass.identifier().key().string().toLowerCase() + "/" +field.name() + ".java").toFile())) {
                writer.write(compilationUnit.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Satellite.registry().write();
    }

    private CompilationUnit generateMessageClass(SatelliteField.MessageField field) {
        CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration("me.combimagnetron.generated.satelliteserviceexample");
        compilationUnit.addImport(ByteBuffer.class);

        RecordDeclaration classDeclaration = new RecordDeclaration(NodeList.nodeList(Modifier.publicModifier()) ,field.name());
        classDeclaration.setImplementedTypes(NodeList.nodeList(new ClassOrInterfaceType(Message.class.getName())));
        field.parameters().forEach(parameter -> {
            Type type = type(parameter.type());
            classDeclaration.addParameter(type, parameter.name());
        });
        compilationUnit.addType(classDeclaration);

        MethodDeclaration writeMethod = classDeclaration.addMethod("write", Modifier.Keyword.PUBLIC);
        writeMethod.setType(void.class);
        writeMethod.addAnnotation(Override.class);
        writeMethod.getBody().ifPresent(body -> {
            body.addStatement("final ByteBuffer buffer = buffer();");
            field.parameters().forEach(parameter -> {
                String adapterName = adapterName(parameter.type());
                String parameterName = parameter.name();
                if (parameter.type().type() == SatelliteField.TypeField.Dummy.class) {
                    parameterName = "org.apache.commons.lang3.ArrayUtils.toObject(" + parameterName + ".serialize())";
                }
                body.addStatement("buffer.write(ByteBuffer.Adapter." + adapterName + ", " + parameterName + ");");
            });
        });

        MethodDeclaration id = classDeclaration.addMethod("id", Modifier.Keyword.PUBLIC);
        id.setType(int.class);
        id.addAnnotation(Override.class);
        id.getBody().ifPresent(body -> body.addStatement("return " + field.id() + ";"));

        MethodDeclaration buffer = classDeclaration.addMethod("buffer", Modifier.Keyword.PUBLIC);
        buffer.setType(ByteBuffer.class);
        buffer.addAnnotation(Override.class);
        buffer.getBody().ifPresent(body -> body.addStatement("return ByteBuffer.empty();"));
        return compilationUnit;
    }

    private CompilationUnit generateTypeClass(SatelliteField.TypeField field) {
        CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration("me.combimagnetron.generated.satelliteserviceexample");
        compilationUnit.addImport(ByteBuffer.class);

        RecordDeclaration classDeclaration = new RecordDeclaration(NodeList.nodeList(Modifier.publicModifier()) ,field.name());
        classDeclaration.setImplementedTypes(NodeList.nodeList(new ClassOrInterfaceType("me.combimagnetron.comet.data.Type<" + field.name() + ">")));
        field.parameters().forEach(parameter -> {
            Type type = type(parameter.type());
            classDeclaration.addParameter(type, parameter.name());
        });
        compilationUnit.addType(classDeclaration);

        MethodDeclaration serializeMethod = classDeclaration.addMethod("serialize", Modifier.Keyword.PUBLIC);
        serializeMethod.setType(byte[].class);
        serializeMethod.addAnnotation(Override.class);
        serializeMethod.getBody().ifPresent(body -> {
            body.addStatement("final ByteBuffer buffer = ByteBuffer.empty();");
            field.parameters().forEach(parameter -> {
                String adapterName = adapterName(parameter.type());
                body.addStatement("buffer.write(ByteBuffer.Adapter." + adapterName + ", " + parameter.name() + ");");
            });
            body.addStatement("return buffer.bytes();");
        });

        MethodDeclaration typeMethod = classDeclaration.addMethod("type", Modifier.Keyword.PUBLIC);
        typeMethod.setType(StaticJavaParser.parseType("java.lang.Class<" + field.name() +">"));
        typeMethod.addAnnotation(Override.class);
        typeMethod.getBody().ifPresent(body -> body.addStatement("return " + field.name() + ".class;"));

        return compilationUnit;
    }

    private Type type(RegisteredType<?> type) {
        if (type.type() == SatelliteField.TypeField.Dummy.class) {
            return StaticJavaParser.parseType(capitalizeFirstLetter(type.name()));

        }
        return StaticJavaParser.parseType(type.type().getName());
    }

    private String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private String adapterName(RegisteredType<?> type) {
        for (Field declaredField : ByteBuffer.Adapter.class.getDeclaredFields()) {
            try {
                ByteBuffer.Adapter<?> adapter = (ByteBuffer.Adapter<?>) declaredField.get(null);
                if (adapter.equals(type.adapter())) {
                    return declaredField.getName();
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private static String read(Path path) {
        if (path == null || !path.toFile().isFile()) {
            throw new IllegalArgumentException("Path is null");
        }
        try (Stream<String> stream = Files.lines(path)) {
            return stream.collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
