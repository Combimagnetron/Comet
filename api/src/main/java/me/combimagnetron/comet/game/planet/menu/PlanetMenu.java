package me.combimagnetron.comet.game.planet.menu;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.Menu;
import me.combimagnetron.comet.game.menu.draft.Draft;
import me.combimagnetron.comet.game.menu.draft.Edit;
import me.combimagnetron.comet.game.menu.element.Position;
import me.combimagnetron.comet.game.menu.element.div.Div;
import me.combimagnetron.comet.game.menu.element.impl.TextElement;
import me.combimagnetron.comet.game.menu.renderer.Renderer;
import me.combimagnetron.comet.game.menu.style.Style;
import me.combimagnetron.comet.game.menu.style.Text;
import me.combimagnetron.comet.image.Color;
import me.combimagnetron.comet.internal.menu.ChestMenu;
import me.combimagnetron.comet.user.User;

public class PlanetMenu extends Menu.Impl {

    public PlanetMenu(User<?> user) {
        super(user);
    }

    @Override
    public void open(User<?> user) {
        Div div = Div.div(Identifier.of("planet_menu", "background"));
        Edit<Div> editDiv = Edit.div()
                .identifier(Identifier.of("planet_menu", "background"))
                .section()
                .add(
                        TextElement.textElement(Position.pixel(0, 0), Identifier.of("background", "text"), Text.text("Planet Menu")).style(Style.color(), Color.of(255,255,255))
                ).back().done();
        div(div);
        apply(Draft.draft().edit(editDiv));
        Renderer.chest().render(user, this);
    }
}
