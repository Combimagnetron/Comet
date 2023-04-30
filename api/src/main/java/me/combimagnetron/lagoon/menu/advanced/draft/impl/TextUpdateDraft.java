package me.combimagnetron.lagoon.menu.advanced.draft.impl;

import me.combimagnetron.lagoon.menu.advanced.draft.Draft;
import me.combimagnetron.lagoon.menu.advanced.draft.action.Action;
import me.combimagnetron.lagoon.operation.Operation;
import net.kyori.adventure.text.Component;

import java.util.concurrent.TimeUnit;

public class TextUpdateDraft implements Draft<Component, TextUpdateDraft.RefreshRate> {
    private Component component;
    private RefreshRate refreshRate;

    @Override
    public Operation<Void> supply(Component component, RefreshRate refreshRate) {
        return Operation.simple(() -> {
            this.component = component;
            this.refreshRate = refreshRate;
        });
    }

    @Override
    public Operation<Component> finish() {
        return Operation.executable(() -> component);
    }

    @Override
    public Operation<Component> supplyAndFinish(Component component, RefreshRate refreshRate) {
        return Operation.executable(() -> {
            this.component = component;
            this.refreshRate = refreshRate;
            return this.component;
        });
    }

    public record RefreshRate(long period, TimeUnit unit) {
        public static RefreshRate refreshRate(long period, TimeUnit unit) {
            return new RefreshRate(period, unit);
        }
    }

    public class TextUpdateAction extends Action {

    }

}
