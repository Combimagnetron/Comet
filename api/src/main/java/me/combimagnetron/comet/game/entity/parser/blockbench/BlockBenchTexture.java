package me.combimagnetron.comet.game.entity.parser.blockbench;

import java.util.UUID;
import com.google.gson.annotations.SerializedName;

public record BlockBenchTexture(@SerializedName("path") String path, @SerializedName("name")  String name, @SerializedName("folder") String folder,
                                @SerializedName("namespace") String namespace, @SerializedName("id") String id, @SerializedName("particle") boolean particle,
                                @SerializedName("render_mode") String renderMode, @SerializedName("renderSides") String renderSides, @SerializedName("frame_time") int frameTime,
                                @SerializedName("frame_order_type") String frameOrderType, @SerializedName("frame_order") String frameOrder, @SerializedName("frame_interpolate") boolean frameInterpolate,
                                @SerializedName("visible") boolean visible, @SerializedName("mode") String mode, @SerializedName("saved") boolean saved,
                                @SerializedName("uuid") UUID uuid, @SerializedName("source") String source, @SerializedName("relative_path") String relativePath) {

}