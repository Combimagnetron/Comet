package me.combimagnetron.comet.feature.entity.parser.blockbench;

import java.util.Map;
import java.util.UUID;
import com.google.gson.annotations.SerializedName;

public record BlockBenchAnimation(@SerializedName("uuid") UUID uuid, @SerializedName("name") String name, @SerializedName("loop") String loop,
                                  @SerializedName("override") boolean override, @SerializedName("length") double length, @SerializedName("snapping") int snapping,
                                  @SerializedName("selected") boolean selected, @SerializedName("anim_time_update") String animationTimeUpdate, @SerializedName("blend_weight") String blendWeight,
                                  @SerializedName("start_delay") String startDelay, @SerializedName("loop_delay") String loopDelay, @SerializedName("animators") Map<UUID, BlockBenchAnimator> animators) {

}
