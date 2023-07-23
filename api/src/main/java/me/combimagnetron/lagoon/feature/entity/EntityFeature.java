package me.combimagnetron.lagoon.feature.entity;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;
import me.combimagnetron.lagoon.feature.entity.parser.ModelParser;
import me.combimagnetron.lagoon.file.Folder;

import java.io.File;

public class EntityFeature implements Feature {
    private final Folder folder = Folder.empty("entity-feature");
    private final ExternalEntityFeatureComponent externalEntityFeatureComponent;


    public EntityFeature(ExternalEntityFeatureComponent externalEntityFeatureComponent) {
        this.externalEntityFeatureComponent = externalEntityFeatureComponent;
    }

    @Override
    public Identifier identifier() {
        return Identifier.of("feature", "entity");
    }

    @Override
    public void start(StartUpArgument... startUpArguments) {
        if (!(startUpArguments[1] instanceof ModelParserStartUpArgument)) {
            return;
        }
    }

    @Override
    public void terminate() {

    }

    @Override
    public Folder folder() {
        return folder;
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
