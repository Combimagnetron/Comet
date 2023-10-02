package me.combimagnetron.comet.level;

import java.util.Collection;

public interface LevelHandler {

    Level load(Template template);

    Collection<Level> loaded();

}
