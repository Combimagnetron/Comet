package me.combimagnetron.lagoon.event.impl.spigot;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpigotEvent implements Event.FilteredEvent, Listener {

    public SpigotEvent() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Comet.javaPlugin());
    }

    @Override
    public Class<? extends Event> eventType() {
        return SpigotEvent.class;
    }

    @EventHandler
    public void event(org.bukkit.event.Event event) {

    }

}
