package me.combimagnetron.lagoon.feature.entity;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;
import me.combimagnetron.lagoon.feature.entity.model.ModelTemplate;
import me.combimagnetron.lagoon.feature.entity.parser.ModelParser;
import me.combimagnetron.lagoon.feature.entity.parser.blockbench.BlockBenchParser;

import java.io.File;

public class EntityFeature implements Feature {
    @Override
    public Identifier identifier() {
        return Identifier.of("feature", "entity");
    }

    @Override
    public void start(StartUpArgument... startUpArguments) {
        if (!(startUpArguments[0] instanceof FileStartUpArgument) && !(startUpArguments[1] instanceof ModelParserStartUpArgument)) {
            return;
        }
        ModelParser parser = switch (((ModelParserStartUpArgument) startUpArguments[1]).parser()) {
            default -> BlockBenchParser.read(((FileStartUpArgument) startUpArguments[0]).file());
        };
    }

    @Override
    public void terminate() {

    }

    public static class FileStartUpArgument implements StartUpArgument {
        private final File file;

        protected FileStartUpArgument(File file) {
            this.file = file;
        }

        public File file() {
            return file;
        }

        public static FileStartUpArgument argument(File file) {
            return new FileStartUpArgument(file);
        }

    }

    public static class ModelParserStartUpArgument implements StartUpArgument {
        private final ModelParser.Parser parser;

        protected ModelParserStartUpArgument(ModelParser.Parser parser) {
            this.parser = parser;
        }

        public ModelParser.Parser parser() {
            return parser;
        }

        public static ModelParserStartUpArgument argument(ModelParser.Parser parser) {
            return new ModelParserStartUpArgument(parser);
        }

    }

}
