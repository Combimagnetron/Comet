package me.combimagnetron.comet.satellite.compiler;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.satellite.Satellite;
import me.combimagnetron.comet.satellite.matcher.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SatelliteCompiler {
    private final HashMap<String, SatelliteClass> loadedClasses = new HashMap<>();

    private List<Path> get(Path path) {
        try (var stream = Files.walk(path, 1, FileVisitOption.FOLLOW_LINKS)) {
            return stream
                    .filter(p -> !p.equals(path))
                    .filter(Files::exists)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generate(Path path, Path compileTo) {
        List<Path> paths = get(path);
        Satellite.registry().paths(paths);
        for (Path file : paths) {
            if (!file.toString().endsWith(".sat")) continue;
            System.out.println("Compiling " + file.getFileName());
            TokenizedResult result = TokenMatcher.matcher(read(file)).section(
                    MatcherSection.section()
                            .token(MatcherToken.required(Token.Type.CLASS))
            ).validate();
            String content = result.ordered().next().token().captured();
            if (content.contains("extends")) {
                String depend = content.split("extends ")[1].split("\\{")[0].trim();
                SatelliteDependency dependency = SatelliteDependency.of(depend);
                if (loadedClasses.get(dependency.dependsOn()) == null) {
                    paths.stream().filter(f -> f.getFileName().endsWith(dependency.dependsOn() + ".sat")).forEach(f -> {
                        SatelliteClass dependsOn = transform(f);
                        compile(dependsOn, compileTo);
                    });
                }
            }
            SatelliteClass clazz = transform(file);
            compile(clazz, compileTo);
        }
        Satellite.registry().write();
    }

    public SatelliteClass transform(Path path) {
        System.out.println("Transforming " + path.getFileName());
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
        tokenizedClass.all(Token.Type.METHOD).stream().sorted((res, res2) -> {
            boolean s1HasType = res.token().captured().contains("type");
            boolean s2HasType = res2.token().captured().contains("type");
            return Boolean.compare(s2HasType, s1HasType);
        }).forEach(result -> {
            TokenizedResult method = result.matcher()
                    .section(
                            MatcherSection.section()
                                    .token(MatcherToken.required(Token.Type.of("responseless")))
                                    .token(MatcherToken.optional(Token.Type.KEYWORD))
                                    .token(MatcherToken.required(Token.Type.OBJECT))
                                    .token(MatcherToken.multiple(Token.Type.KEYWORD_OBJECT_PAIR))
                                    .token(MatcherToken.optional(Token.Type.of("( : \\w+[\\<\\w\\>]+)")))
                    )
                    .validate();
            fields.add(SatelliteField.field(method, path.getFileName().toString().toLowerCase()));
        });
        SatelliteDependency dependency = SatelliteDependencyResolver.resolve(tokenizedClass);
        /* TODO
            move to api package and rename satellite to lexer/parser so it can be used for future other scripting languages/config files.
            generate java service class
            fix id registry
            implement responseless and repeated keywords
         */
        SatelliteClass satelliteClass = SatelliteClass.of(identifier, dependency, fields);
        loadedClasses.put(identifier.key().string(), satelliteClass);
        return satelliteClass;
    }

    public void compile(SatelliteClass satelliteClass, Path path) {
        List<SatelliteField.MethodField> methods = new ArrayList<>();
        for (SatelliteField field : satelliteClass.fields()) {
            if (field instanceof SatelliteField.MessageField messageField) {
                generateMessageClass(messageField, satelliteClass, path);
            } else if (field instanceof SatelliteField.TypeField typeField) {
                generateTypeClass(typeField, satelliteClass, path);
            } else if (field instanceof SatelliteField.MethodField methodField) {
                methods.add(methodField);
                generateMessageClass(methodField.message(), satelliteClass, path);
            }

        }
        System.out.println(methods.size());
        generateServiceClass(satelliteClass, path, methods);
    }

    private void write(CompilationUnit compilationUnit, SatelliteClass satelliteClass, Path path, SatelliteField field) {
        path.resolve(satelliteClass.identifier().key().string().toLowerCase()).toFile().mkdirs();
        try (FileWriter writer = new FileWriter(path.resolve(satelliteClass.identifier().key().string().toLowerCase() + "/" + field.name() + ".java").toFile())) {
            writer.write(compilationUnit.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateServiceClass(SatelliteClass satelliteClass, Path path, List<SatelliteField.MethodField> methods) {
        String name = satelliteClass.identifier().key().string();
        CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration("me.combimagnetron.generated" + "." + satelliteClass.identifier().key().string().toLowerCase());
        compilationUnit.addImport(ByteBuffer.class);
        compilationUnit.addImport("me.combimagnetron.generated." + satelliteClass.identifier().key().string().toLowerCase() + ".*");
        compilationUnit.addImport("me.combimagnetron.comet.communication.Message");
        compilationUnit.addImport("me.combimagnetron.generated.baseservice.*");
        ClassOrInterfaceDeclaration classDeclaration = compilationUnit.addClass(name);
        classDeclaration.addImplementedType("me.combimagnetron.comet.service.Service");
        for (SatelliteField.MethodField method : methods) {
            MethodDeclaration methodDeclaration = classDeclaration.addMethod(decapitalizeFirstLetter(method.name()), Modifier.Keyword.PUBLIC);
            methodDeclaration.setType(type(method.type()));
            methodDeclaration.getBody().ifPresent(body -> {
                body.addStatement("channel.send(" + method.message().name() + ".of(" + method.message().parameters().stream().map(SatelliteField.VariableParameter::name).collect(Collectors.joining(", ")) + "));");
                if (!Objects.equals(method.type().name(), "Void")) {
                    body.addStatement("return null" /*+ method.message().name()*/ + ";");
                }
            });
            method.message().parameters().forEach(parameter -> {
                String parameterName = parameter.name();
                methodDeclaration.addParameter(type(parameter.type()), parameterName);
            });
        }
        write(compilationUnit, satelliteClass, path, new SatelliteField.MethodField(name, "service", RegisteredType.BYTE, (SatelliteField.VariableParameter) null));
    }

    private void generateMessageClass(SatelliteField.MessageField field, SatelliteClass satelliteClass, Path path) {
        CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration("me.combimagnetron.generated" + "." + satelliteClass.identifier().key().string().toLowerCase());
        compilationUnit.addImport(ByteBuffer.class);
        if (satelliteClass.dependency() != null) {
            compilationUnit = compilationUnit.addImport("me.combimagnetron.generated." + satelliteClass.dependency().dependsOn().toLowerCase() + ".*");
        }
        RecordDeclaration classDeclaration = new RecordDeclaration(NodeList.nodeList(Modifier.publicModifier()), field.name());
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
            body.addStatement("buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());");
            field.parameters().forEach(parameter -> {
                String adapterName = adapterName(parameter.type());
                String parameterName = parameter.name();
                if (parameter.type().type() == SatelliteField.TypeField.Dummy.class) {
                    parameterName = "org.apache.commons.lang3.ArrayUtils.toObject(" + parameterName + ".serialize())";
                }
                body.addStatement("buffer.write(ByteBuffer.Adapter." + adapterName + ", " + parameterName + ");");
            });
        });

        MethodDeclaration ofMethod = classDeclaration.addMethod("of", Modifier.Keyword.PUBLIC, Modifier.Keyword.STATIC);
        ofMethod.setType(classDeclaration.getName().asString());
        field.parameters().forEach(parameter -> {
            Type type = type(parameter.type());
            ofMethod.addParameter(type, parameter.name());
        });
        ofMethod.getBody().ifPresent(body -> {
            StringBuilder builder = new StringBuilder();
            field.parameters().forEach(parameter -> {
                builder.append(parameter.name());
                if (((List<?>)field.parameters()).indexOf(parameter) != field.parameters().size() - 1) {
                    builder.append(", ");
                }
            });
            body.addStatement("return new " + field.name() + "("+ builder +");");
        });

        MethodDeclaration bytesMethod = classDeclaration.addMethod("of", Modifier.Keyword.PUBLIC, Modifier.Keyword.STATIC);
        bytesMethod.setType(classDeclaration.getName().asString());
        bytesMethod.addParameter(byte[].class, "bytes");
        bytesMethod.getBody().ifPresent(body -> {
            body.addStatement("final ByteBuffer buffer = ByteBuffer.of(bytes);");
            field.parameters().forEach(parameter -> {
                String adapterName = adapterName(parameter.type());
                if (parameter.type().type() == SatelliteField.TypeField.Dummy.class) {
                    body.addStatement(type(parameter.type()).toString() + " " + parameter.name() + " = " + parameter.type().name() + ".of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));");
                } else {
                    body.addStatement(type(parameter.type()).toString() + " " + parameter.name() + " = buffer.read(ByteBuffer.Adapter." + adapterName + ");");
                }
            });
            body.addStatement("return new " + field.name() + "(" + field.parameters().stream().map(SatelliteField.VariableParameter::name).collect(Collectors.joining(", ")) + ");");
        });

        MethodDeclaration id = classDeclaration.addMethod("id", Modifier.Keyword.PUBLIC);
        id.setType(int.class);
        id.addAnnotation(Override.class);
        id.getBody().ifPresent(body -> body.addStatement("return " + field.id() + ";"));

        MethodDeclaration buffer = classDeclaration.addMethod("buffer", Modifier.Keyword.PUBLIC);
        buffer.setType(ByteBuffer.class);
        buffer.addAnnotation(Override.class);
        buffer.getBody().ifPresent(body -> body.addStatement("return ByteBuffer.empty();"));
        write(compilationUnit, satelliteClass, path, field);
    }

    private void generateTypeClass(SatelliteField.TypeField field, SatelliteClass satelliteClass, Path path) {
        CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration("me.combimagnetron.generated" + "." + satelliteClass.identifier().key().string().toLowerCase());
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
                String parameterName = parameter.name();
                if (parameter.type().type() == SatelliteField.TypeField.Dummy.class) {
                    parameterName = "org.apache.commons.lang3.ArrayUtils.toObject(" + parameterName + ".serialize())";
                }
                body.addStatement("buffer.write(ByteBuffer.Adapter." + adapterName + ", " + parameterName + ");");
            });
            body.addStatement("return buffer.bytes();");
        });

        MethodDeclaration bytesMethod = classDeclaration.addMethod("of", Modifier.Keyword.PUBLIC, Modifier.Keyword.STATIC);
        bytesMethod.setType(classDeclaration.getName().asString());
        bytesMethod.addParameter(byte[].class, "bytes");
        bytesMethod.getBody().ifPresent(body -> {
            body.addStatement("final ByteBuffer buffer = ByteBuffer.of(bytes);");
            field.parameters().forEach(parameter -> {
                String adapterName = adapterName(parameter.type());
                if (parameter.type().type() == SatelliteField.TypeField.Dummy.class) {
                    body.addStatement(type(parameter.type()).toString() + " " + parameter.name() + " = " + parameter.type().name() + ".of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));");
                } else {
                    body.addStatement(type(parameter.type()).toString() + " " + parameter.name() + " = buffer.read(ByteBuffer.Adapter." + adapterName + ");");
                }
            });
            body.addStatement("return new " + field.name() + "(" + field.parameters().stream().map(SatelliteField.VariableParameter::name).collect(Collectors.joining(", ")) + ");");
        });

        MethodDeclaration typeMethod = classDeclaration.addMethod("type", Modifier.Keyword.PUBLIC);
        typeMethod.setType(StaticJavaParser.parseType("java.lang.Class<" + field.name() +">"));
        typeMethod.addAnnotation(Override.class);
        typeMethod.getBody().ifPresent(body -> body.addStatement("return " + field.name() + ".class;"));
        write(compilationUnit, satelliteClass, path, field);
    }

    private Type type(RegisteredType<?> type) {
        if (Objects.equals(type.name(), "Void")) {
            return StaticJavaParser.parseType("void");
        } else if (Objects.equals(type.name(), "Byte[]")) {
            return ArrayType.wrapInArrayTypes(StaticJavaParser.parseType("byte"));
        }
        if (type.type() == SatelliteField.TypeField.Dummy.class) {
            if (type.name().contains("mul<")) {
                String[] split = type.name().split(Pattern.quote("<"));
                String[] split2 = split[1].split(Pattern.quote(">"));
                return StaticJavaParser.parseType("java.util.List<" + split2[0] + ">");
            }
            return StaticJavaParser.parseType(capitalizeFirstLetter(type.name()));
        }
        if (type.name().contains("mul<")) {
            String[] split = type.name().split(Pattern.quote("<"));
            String[] split2 = split[1].split(Pattern.quote(">"));
            return StaticJavaParser.parseType("java.util.List<" + split2[0] + ">");
        }
        return StaticJavaParser.parseType(type.type().getName());
    }

    private String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private String decapitalizeFirstLetter(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
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
