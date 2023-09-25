package me.combimagnetron.lagoon.level;

import java.util.Collection;

public interface LevelHandler {

    Level load(Template template);

    Collection<Level> loaded();

}
