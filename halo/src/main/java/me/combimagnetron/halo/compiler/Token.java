package me.combimagnetron.halo.compiler;

import java.util.regex.Pattern;

public record Token(Type type, String captured) {

    public interface Type {
        Type OBJECT = Impl.of("([A-Z]\\w*)");
        Type METHOD_REFERENCE = Impl.of("((repeated )?([A-Z]\\w* ([a-z_]+)+))");
        Type START = Impl.of("([a-z]\\w+-bound message \\w+ \\{)");
        Type TYPE_ENUM = Impl.of("type \\w* {\\n\\s+(([A-Z]+)+,*\\s*)+}");
        Type TYPE_CLASS = Impl.of("type \\w*\\(((.*?) ([a-z]+)+,*)*\\) {\\n+\\s+}");
        Type CLASS = Impl.of("([a-z]\\w+-bound message \\w+ \\{\\s*(((.*?) ([a-zA-Z_]+)+,*)\\n*\\s*)+\\})");
        Type NUMBER = Impl.of("[\\d\\.]+");
        Type COMMENT = Impl.of("//.*");
        Type COMMENT_BLOCK = Impl.of("((\\/\\*)[\\s\\S]*(\\*\\/))");
        Type[] ALL = new Type[]{OBJECT, METHOD_REFERENCE, NUMBER, START, TYPE_ENUM, TYPE_CLASS, CLASS, COMMENT, COMMENT_BLOCK};

        Pattern pattern();

        record Impl<T>(String literalPattern) implements Type {
            public static <T> Impl<T> of(String literalPattern) {
                return new Impl<>(literalPattern);
            }

            @Override
            public Pattern pattern() {
                return Pattern.compile(literalPattern);
            }
        }

    }

}
