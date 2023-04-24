package me.combimagnetron.lagoon.player;

import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.menu.advanced.Cursor;
import me.combimagnetron.lagoon.menu.advanced.application.Application;
import me.combimagnetron.lagoon.menu.canvas.Canvas;
import me.combimagnetron.lagoon.menu.advanced.render.RenderSystem;
import me.combimagnetron.lagoon.operation.Operable;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.data.DataRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.UUID;

public interface GlobalPlayer<T extends Audience> extends Operable {
     T platformSpecificPlayer();

     void sendMessage(Component component);

     Instance instance();

     DataRegistry playerData();

     UUID uniqueIdentifier();

     Canvas canvas();

     Collection<Application> openApplications();

     RenderSystem renderSystem();

     Object runOperation(Operation<?> operation);

     Cursor<?> cursor();
    
     boolean inMenu();

     Operation<Void> openApp(Application application);

}
