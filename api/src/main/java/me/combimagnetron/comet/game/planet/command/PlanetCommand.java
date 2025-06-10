package me.combimagnetron.comet.game.planet.command;

import me.combimagnetron.comet.command.annotations.Command;
import me.combimagnetron.comet.command.annotations.Execute;
import me.combimagnetron.comet.user.User;

@Command(command = "planet", aliases = {"pl", "plt"})
public class PlanetCommand {

    @Execute
    public void execute(User<?> user) {

    }


}
