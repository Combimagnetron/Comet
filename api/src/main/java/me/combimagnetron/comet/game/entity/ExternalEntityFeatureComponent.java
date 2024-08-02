package me.combimagnetron.comet.game.entity;

import me.combimagnetron.comet.game.ExternalFeatureComponent;
import me.combimagnetron.comet.game.entity.model.ModelTemplate;
import me.combimagnetron.comet.game.entity.model.ModeledEntity;
import me.combimagnetron.comet.internal.entity.Entity;
import me.combimagnetron.comet.user.User;

public abstract class ExternalEntityFeatureComponent extends ExternalFeatureComponent {
    public ExternalEntityFeatureComponent(EntityFeature feature) {
        super(feature, OutsourceReason.VERSION_DEPENDENT_IMPLEMENTATION);
    }

    public abstract ModeledEntity spawnModel(User<?> user, ModelTemplate template, Entity baseEntity);

}
