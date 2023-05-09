package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.feature.entity.EntityFeature;
import me.combimagnetron.lagoon.feature.entity.ExternalEntityFeatureComponent;
import me.combimagnetron.lagoon.feature.entity.model.ModelTemplate;
import me.combimagnetron.lagoon.feature.entity.model.ModeledEntity;
import me.combimagnetron.lagoon.model.ModeledEntityImpl;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.player.GlobalPlayer;
import org.bukkit.entity.Entity;

public class ExternalEntityFeatureComponentImpl extends ExternalEntityFeatureComponent {
    public ExternalEntityFeatureComponentImpl(EntityFeature feature) {
        super(feature);
    }

    @Override
    public Operation<ModeledEntity> spawnModel(GlobalPlayer<?> player, ModelTemplate template, Entity baseEntity) {
        return Operation.executable(() -> new ModeledEntityImpl(template, baseEntity));
    }
}
