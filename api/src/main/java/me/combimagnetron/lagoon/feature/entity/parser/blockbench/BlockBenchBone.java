package me.combimagnetron.lagoon.feature.entity.parser.blockbench;

import java.util.UUID;
import com.google.gson.annotations.SerializedName;

    public record BlockBenchBone(@SerializedName("name") String name, @SerializedName("origin") double[] origin, @SerializedName("rotation") double[] rotation,
                                 @SerializedName("color") int color, @SerializedName("bone_type") String boneType, @SerializedName("uuid") UUID uuid, @SerializedName("export") boolean export,
                                 @SerializedName("mirror_uv") boolean mirrorUv, @SerializedName("isOpen") boolean isOpen, @SerializedName("locked") boolean locked,
                                 @SerializedName("visibility") boolean visibility, @SerializedName("autoUv") int autoUv, @SerializedName("children") BlockBenchChildren[] children)
    implements BlockBenchChildren {

    public BlockBenchBone(String name, double[] origin, double[] rotation,
                          int color, String boneType, UUID uuid,
                          boolean export, boolean mirrorUv, boolean isOpen,
                          boolean locked, boolean visibility, int autoUv) {
        this(name, origin, rotation, color, boneType, uuid, export, mirrorUv ,isOpen, locked, visibility, autoUv, new BlockBenchChildren[]{});
    }

}