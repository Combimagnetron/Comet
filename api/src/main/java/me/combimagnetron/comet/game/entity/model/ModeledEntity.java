package me.combimagnetron.comet.game.entity.model;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.entity.animation.Timeline;
import me.combimagnetron.comet.game.entity.math.Point;
import me.combimagnetron.comet.internal.entity.Entity;
import me.combimagnetron.comet.internal.entity.metadata.type.Rotation;

public interface ModeledEntity {

    Identifier identifier();

    Timeline timeline();

    ModelTemplate template();

    void teleport(Point point);

    void rotate(String name, Rotation angle);

    void mountEntity(Entity entity);

    void dismountEntity(Entity entity);

    void headRotation(Rotation angle);

    void position();

    void tick();

}
