package me.combimagnetron.comet.feature.entity;

import me.combimagnetron.comet.feature.entity.model.ModeledEntityImpl;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.feature.entity.model.ModelTemplate;
import me.combimagnetron.comet.feature.entity.model.ModeledEntity;
import me.combimagnetron.comet.operation.Operation;
import me.combimagnetron.comet.user.User;
import org.bukkit.entity.Entity;

public class ExternalEntityFeatureComponentImpl extends ExternalEntityFeatureComponent {
    public ExternalEntityFeatureComponentImpl(EntityFeature feature) {
        super(feature);
    }

    @Override
    public ModeledEntity spawnModel(User<?> player, ModelTemplate template, Entity baseEntity) {
        return new ModeledEntityImpl(template, baseEntity);
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
