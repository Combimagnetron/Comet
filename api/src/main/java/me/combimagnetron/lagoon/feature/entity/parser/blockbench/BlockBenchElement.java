package me.combimagnetron.lagoon.feature.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;
import me.combimagnetron.lagoon.feature.entity.generator.Cube;
import me.combimagnetron.lagoon.feature.entity.generator.Face;
import me.combimagnetron.lagoon.feature.entity.generator.Rotation;
import org.bukkit.util.EulerAngle;

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
        return new Cube(from, to, new Rotation(new EulerAngle(
                        -Math.toRadians(rotation[0]),
                        -Math.toRadians(rotation[1]),
                        Math.toRadians(rotation[2]))
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