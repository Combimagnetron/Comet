package me.combimagnetron.lagoon.feature.entity;

import me.combimagnetron.lagoon.feature.ExternalFeatureComponent;
import me.combimagnetron.lagoon.feature.entity.model.ModelTemplate;
import me.combimagnetron.lagoon.feature.entity.model.ModeledEntity;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.entity.Entity;

public abstract class ExternalEntityFeatureComponent extends ExternalFeatureComponent {
    public ExternalEntityFeatureComponent(EntityFeature feature) {
        super(feature, OutsourceReason.VERSION_DEPENDENT_IMPLEMENTATION);
    }

    public abstract Operation<ModeledEntity> spawnModel(User<?> user, ModelTemplate template, Entity baseEntity);

}
