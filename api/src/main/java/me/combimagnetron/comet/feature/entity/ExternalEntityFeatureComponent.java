package me.combimagnetron.comet.feature.entity;

import me.combimagnetron.comet.feature.ExternalFeatureComponent;
import me.combimagnetron.comet.feature.entity.model.ModelTemplate;
import me.combimagnetron.comet.feature.entity.model.ModeledEntity;
import me.combimagnetron.comet.user.User;
import me.combimagnetron.comet.operation.Operation;
import org.bukkit.entity.Entity;

public abstract class ExternalEntityFeatureComponent extends ExternalFeatureComponent {
    public ExternalEntityFeatureComponent(EntityFeature feature) {
        super(feature, OutsourceReason.VERSION_DEPENDENT_IMPLEMENTATION);
    }

    public abstract Operation<ModeledEntity> spawnModel(User<?> user, ModelTemplate template, Entity baseEntity);

}
