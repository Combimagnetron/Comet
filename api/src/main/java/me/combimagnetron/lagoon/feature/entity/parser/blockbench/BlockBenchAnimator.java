package me.combimagnetron.lagoon.feature.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;

public record BlockBenchAnimator(@SerializedName("name") String name, @SerializedName("type") String type, @SerializedName("keyframes") BlockBenchKeyframe[] keyframes) {
}