package me.combimagnetron.comet.game.entity.parser.blockbench;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import me.combimagnetron.comet.game.entity.parser.blockbench.deserializer.ChildrenDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

public record BlockBenchModel(@SerializedName("meta") BlockBenchMeta meta, @SerializedName("name") String name, @SerializedName("model_identifier") String modelIdentifier,
                              @SerializedName("visible_box") double[] visibleBox, @SerializedName("variable_placeholders") String variablePlaceholders, @SerializedName("resolution") BlockBenchResolution resolution,
                              @SerializedName("elements") BlockBenchElement[] elements, @SerializedName("bones") BlockBenchBone[] bones, @SerializedName("textures") BlockBenchTexture[] textures,
                              @SerializedName("animations") BlockBenchAnimation[] animations) {

    public static BlockBenchModel read(Path file) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BlockBenchChildren.class, new ChildrenDeserializer(gsonBuilder));
        BlockBenchModel model;
        try {
            model = gsonBuilder.create().fromJson(new FileReader(file.toFile()), BlockBenchModel.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

}