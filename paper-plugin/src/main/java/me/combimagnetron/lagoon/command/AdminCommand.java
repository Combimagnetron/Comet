package me.combimagnetron.lagoon.command;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.command.annotations.Command;
import me.combimagnetron.lagoon.command.annotations.Execute;
import me.combimagnetron.lagoon.command.annotations.Requires;
import me.combimagnetron.lagoon.command.annotations.SubCommand;
import me.combimagnetron.lagoon.command.argument.impl.StringArgument;
import me.combimagnetron.lagoon.feature.entity.generator.EntityModelGenerator;
import me.combimagnetron.lagoon.feature.entity.parser.blockbench.BlockBenchModel;
import me.combimagnetron.lagoon.user.User;

import java.nio.file.Path;

@Command(command = "c")
public class AdminCommand {

    @Execute
    public void execute(User<?> user, StringArgument stringArgument) {
        BlockBenchModel model = BlockBenchModel.read(Path.of(Comet.javaPlugin().getDataFolder().getPath(), "frog.bbmodel"));
        new EntityModelGenerator().generate(model);
    }

    @SubCommand(command = "test")
    @Requires(condition = "user.group.weight >= 6")
    public void subCommand(User<?> user, StringArgument stringArgument) {

    }


}
