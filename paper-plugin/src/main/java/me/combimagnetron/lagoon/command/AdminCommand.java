package me.combimagnetron.lagoon.command;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.command.annotations.*;
import me.combimagnetron.lagoon.feature.entity.generator.EntityModelGenerator;
import me.combimagnetron.lagoon.feature.entity.parser.blockbench.BlockBenchModel;
import me.combimagnetron.lagoon.user.User;

@Command(command = "c")
public class AdminCommand {

    @Execute
    public void execute(User<?> user) {

    }

    @SubCommand(command = "model")
    @Requires(condition = "user.group.weight >= 6 && user.name != Herobrine")
    public void subCommand(User<?> user, String string) {
        BlockBenchModel model = BlockBenchModel.read(Comet.dataFolder().resolve(string));
        EntityModelGenerator.generate(model);
    }

}
