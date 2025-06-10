package me.combimagnetron.comet.game.menu.element.impl;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.event.impl.menu.UserClickElementEvent;
import me.combimagnetron.comet.event.impl.menu.UserHoverElementEvent;
import me.combimagnetron.comet.game.menu.Pos2D;
import me.combimagnetron.comet.game.menu.element.Interactable;
import me.combimagnetron.comet.game.menu.element.Position;
import me.combimagnetron.comet.game.menu.element.SimpleBufferedElement;
import me.combimagnetron.comet.image.Canvas;
import me.combimagnetron.comet.user.User;

import java.util.function.Consumer;

public class ButtonElement extends SimpleBufferedElement<ButtonElement> implements Interactable {
    private Canvas icon;

    private Consumer<UserHoverElementEvent> hoverElementEventConsumer = (x) -> {};
    private Consumer<UserClickElementEvent> clickElementEventConsumer = (x) -> {};

    public ButtonElement(int width, Position position, Identifier identifier) {
        super(Pos2D.of(width, 10), identifier, position);
    }

    public ButtonElement(Pos2D size, Position position, Identifier identifier) {
        super(size, identifier, position);
    }

    public static ButtonElement buttonElement(int width, Position position, Identifier identifier) {
        return new ButtonElement(width, position, identifier);
    }

    public static ButtonElement buttonElement(Position position, Identifier identifier, Canvas icon) {
        ButtonElement buttonElement = new ButtonElement(icon.size(), position, identifier);
        buttonElement.icon(icon);
        return buttonElement;
    }

    public void icon(Canvas icon) {
        this.icon = icon;
    }

    public void click(User<?> user) {
        this.clickElementEventConsumer.accept(UserClickElementEvent.of(user, this));
    }

    public void hover(User<?> user) {
        this.hoverElementEventConsumer.accept(UserHoverElementEvent.of(user, this));
    }

    @Override
    public void hover(Consumer<UserHoverElementEvent> consumer) {
        this.hoverElementEventConsumer = consumer;
    }

    @Override
    public void click(Consumer<UserClickElementEvent> consumer) {
        this.clickElementEventConsumer = consumer;
    }

    @Override
    protected Canvas render(Canvas image) {
        if (icon != null) {
            image = image.place(icon, Pos2D.of(0, 0));
        }
        return image;
    }
}
