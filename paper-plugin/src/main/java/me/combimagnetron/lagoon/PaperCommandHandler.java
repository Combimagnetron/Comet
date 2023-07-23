package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.command.CommandHandler;
import me.combimagnetron.lagoon.command.InternalCommand;
import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.command.argument.impl.StringArgument;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class PaperCommandHandler extends CommandHandler implements CommandExecutor, Listener {
    private final Collection<InternalCommand> commands;

    public PaperCommandHandler() {
        commands = commands();
        convert();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        InternalCommand internalCommand = commands.stream().filter(internal -> command.getName().equals(internal.format().string()) || command.getAliases().contains(internal.format().string())).findFirst().orElse(null);
        if (!(sender instanceof Player player)) {
            return false;
        }
        User<?> user = Comet.userByUniqueId(player.getUniqueId());
        internalCommand.run(user).async();
        return true;
    }

    void convert() {
        for (InternalCommand command : commands) {
            PaperCommand paperCommand = new PaperCommand(command.format().string(), command);
            Bukkit.getCommandMap().register(command.format().string(), paperCommand);
        }
    }

    static class PaperCommand extends Command {
        private final InternalCommand command;

        protected PaperCommand(@NotNull String name, InternalCommand command) {
            super(name);
            this.command = command;
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender instanceof Player player)) {
                return false;
            }
            command.run(Comet.userByUniqueId(player.getUniqueId()), new StringArgument("frog.bbmodel")).async();
            return true;
        }

        @Override
        public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
            return List.of("cheese", "banana");
        }

        private Argument<?>[] arguments(InternalCommand command, String[] args) {
            return command.format().arguments();
        }



    }

}
