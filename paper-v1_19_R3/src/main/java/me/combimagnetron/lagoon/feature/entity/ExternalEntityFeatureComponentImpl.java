package me.combimagnetron.lagoon.feature.entity;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.entity.model.ModelTemplate;
import me.combimagnetron.lagoon.feature.entity.model.ModeledEntity;
import me.combimagnetron.lagoon.feature.entity.model.ModeledEntityImpl;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.service.Action;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.entity.Entity;

import java.util.Set;

public class ExternalEntityFeatureComponentImpl extends ExternalEntityFeatureComponent {
    public ExternalEntityFeatureComponentImpl(EntityFeature feature) {
        super(feature);
    }

    @Override
    public Operation<ModeledEntity> spawnModel(User<?> player, ModelTemplate template, Entity baseEntity) {
        return Operation.executable(() -> new ModeledEntityImpl(template, baseEntity));
    }

    @Override
    public Identifier identifier() {
        return null;
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

}
