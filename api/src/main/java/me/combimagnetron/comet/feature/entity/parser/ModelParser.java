package me.combimagnetron.comet.feature.entity.parser;

import me.combimagnetron.comet.feature.entity.animation.Animation;
import me.combimagnetron.comet.feature.entity.model.bone.Bone;
import me.combimagnetron.comet.operation.Operation;

import java.util.Collection;

public interface ModelParser {

    Operation<Collection<Bone>> bones();

    Operation<Collection<Animation>> animations();

    enum Parser {
        BLOCKBENCH
    }

}
