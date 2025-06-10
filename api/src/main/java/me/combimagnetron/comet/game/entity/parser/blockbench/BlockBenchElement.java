package me.combimagnetron.comet.game.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;
import me.combimagnetron.comet.game.entity.generator.Cube;
import me.combimagnetron.comet.game.entity.generator.Face;
import me.combimagnetron.comet.game.entity.generator.Rotation;

import java.util.*;

public record BlockBenchElement(@SerializedName("name") String name, @SerializedName("box_uv") boolean boxUv, @SerializedName("rescale") boolean rescale,
                                @SerializedName("locked") boolean locked, @SerializedName("from") double[] from, @SerializedName("to") double[] to,
                                @SerializedName("autouv") int autoUv, @SerializedName("color") int color, @SerializedName("inflate") double inflate,
                                @SerializedName("rotation") double[] rotation, @SerializedName("origin") double[] origin, @SerializedName("uv_offset") int[] uvOffset,
                                @SerializedName("faces") BlockBenchFaces faces, @SerializedName("type") String type, @SerializedName("uuid") UUID uuid) {

    public Cube cube(BlockBenchResolution resolution) {
        double[] rotation = this.rotation;
        if (rotation == null) {
            rotation = new double[]{0.0, 0.0, 0.0};
        }
        return new Cube(from, to, new Rotation(null
                , origin), faces(resolution));
    }

    private Map<String, Face> faces(BlockBenchResolution resolution) {
        Map<String, Face> faceMap = new HashMap<>();
        for (Map.Entry<String, BlockBenchFace> blockBenchFace :
                Set.of(
                        Map.entry("north", faces.north()), Map.entry("east", faces.east()), Map.entry("south", faces.south()),
                        Map.entry("west", faces.west()), Map.entry("up", faces.up()), Map.entry("down", faces.down()))
        ) {
            Face face = new Face(blockBenchFace.getValue(), resolution);
            faceMap.put(blockBenchFace.getKey(), face);
        }
        return faceMap;
    }

}