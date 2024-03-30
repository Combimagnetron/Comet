package me.combimagnetron.comet.command;

import me.combimagnetron.comet.Comet;
import me.combimagnetron.comet.command.annotations.Command;
import me.combimagnetron.comet.command.annotations.Execute;
import me.combimagnetron.comet.command.annotations.Requires;
import me.combimagnetron.comet.command.annotations.SubCommand;
import me.combimagnetron.comet.feature.entity.generator.EntityModelGenerator;
import me.combimagnetron.comet.feature.entity.parser.blockbench.BlockBenchModel;
import me.combimagnetron.comet.internal.entity.impl.display.Display;
import me.combimagnetron.comet.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.comet.internal.entity.impl.hostile.Husk;
import me.combimagnetron.comet.internal.entity.metadata.type.Quaternion;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.internal.network.packet.client.ClientSpawnEntity;
import me.combimagnetron.comet.user.User;
import net.kyori.adventure.text.Component;

@Command(command = "comet", aliases = "c")
public class AdminCommand {

    @Execute
    public void execute(User<?> user) {
        final TextDisplay textDisplay = TextDisplay.nonTracked(user.position(), user);
        textDisplay.text(Component.text("Hello!"));
        textDisplay.transformation(
                Display.Transformation.of(
                        Vector3d.vec3(0, 0, 0),
                        Vector3d.vec3(0, 0, 0),
                        Quaternion.of(0, 0, 0, 0),
                        Quaternion.of(0, 0, 0, 0)
                )
        );
        user.message(Component.text("Spawned!"));
    }

    @SubCommand(command = "model")
    @Requires(condition = "user.group.weight >= 6 && user.name != Herobrine")
    public void model(User<?> user, String string) {
        BlockBenchModel model = BlockBenchModel.read(Comet.dataFolder().resolve(string));
        EntityModelGenerator.generate(model);
    }

}
