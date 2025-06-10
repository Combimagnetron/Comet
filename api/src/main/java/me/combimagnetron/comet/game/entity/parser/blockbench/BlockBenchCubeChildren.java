package me.combimagnetron.comet.game.entity.parser.blockbench;

import java.util.UUID;
import com.google.gson.annotations.SerializedName;

public record BlockBenchCubeChildren(@SerializedName("uuid") UUID uuid) {
}