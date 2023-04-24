package me.combimagnetron.lagoon.game;

import me.combimagnetron.lagoon.game.level.GameLevel;

public record Location(GameLevel level, double x, double y, double z) {
}
