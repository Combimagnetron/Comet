package me.combimagnetron.comet.satellite.compiler;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.satellite.matcher.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SatelliteCompiler {

    void test() {
        TokenizedResult result = TokenMatcher.matcher(null)
                .section(
                        MatcherSection.section()
                                .token(MatcherToken.required(Token.Type.CLASS))
                )
                .validate();
        TokenizedResult.MatcherResult found = result.ordered().next();
        String object = found.matcher()
                .section(
                        MatcherSection.section()
                                .token(MatcherToken.required(Token.Type.OBJECT))
                )
                .validate().ordered().next().token().captured();

    }

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
                                .token(MatcherToken.required(Token.Type.BRACKET_CLOSE))
                )
                .validate();
        Identifier identifier = Identifier.of(tokenizedClass.content(1), tokenizedClass.content(3));
        tokenizedClass.ordered().all().forEach(entry -> System.out.println(entry.token()));
        System.out.println(identifier.string());
        /* TODO
            use Modifier.isStatic on the getDeclaredFields to find the adapter field name instead of hardcoding it.
            resolve dependencies between classes
            matchersection.adjust and rewrite (private MatcherSection combine()) method.
            read types and register them in RegisteredType
            generate java message and type classes
            generate java service class
         */
        return null;
    }

    public void compile(SatelliteClass satelliteClass) {

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
