package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.command.CommandHandler;
import me.combimagnetron.lagoon.command.InternalCommand;
import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.*;

public class PaperCommandHandler extends CommandHandler implements CommandExecutor, Listener {
    private final Collection<InternalCommand> commands;
    @Inject
    private CometBase<?> comet;

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
        User<?> user = comet.users().user(player.getUniqueId()).orElseThrow();
        internalCommand.run(user);
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
        @Inject
        private CometBase<?> comet;

        protected PaperCommand(@NotNull String name, InternalCommand command) {
            super(name);
            this.command = command;
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            if (!(sender instanceof Player player)) {
                return false;
            }
            Set<Object> objectSet = new LinkedHashSet<>();
            Arrays.stream(command.format().arguments()).forEach(argument -> {

            });
            command.run(comet.users().user(player.getUniqueId()).orElseThrow(), Argument.STRING);
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
