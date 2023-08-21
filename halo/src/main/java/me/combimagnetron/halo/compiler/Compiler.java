package me.combimagnetron.halo.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Compiler {

    private void compile(Path path) throws IOException {
        Files.readAllLines(path).forEach(line -> {
            String[] lines = line.split(" ");
            for (String s : lines) {
                Token.Type<?> type = Arrays.stream(Token.Type.ALL).filter(t -> t.pattern().matcher(s).matches()).findAny().orElseThrow();
            }
        });


    }


}
