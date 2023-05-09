package me.combimagnetron.lagoon.feature.entity.parser;

import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.feature.entity.animation.Animation;

import java.util.Collection;

public interface ModelParser {

    Operation<Collection<Bone>> bones();

    Operation<Collection<Animation>> animations();

    enum Parser {
        BLOCKBENCH
    }

}
