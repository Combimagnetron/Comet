package me.combimagnetron.lagoon;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScriptParserImpl {
    private final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private final ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("dsl");

    public void parse(File file) {
        if (!file.getName().endsWith(".kiwi")) {
            return;
        }
        String contents;
        try {
            contents = Files.readString(Path.of(file.toURI()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Object output;
        try {
            output = scriptEngine.eval(contents);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }

    }

    public void parse(String string) {
        Object output;
        try {
            output = scriptEngine.eval(string);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

}
