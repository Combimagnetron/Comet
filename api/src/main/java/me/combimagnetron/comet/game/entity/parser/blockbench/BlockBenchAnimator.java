package me.combimagnetron.comet.game.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;

public record BlockBenchAnimator(@SerializedName("name") String name, @SerializedName("type") String type, @SerializedName("keyframes") BlockBenchKeyframe[] keyframes) {
}