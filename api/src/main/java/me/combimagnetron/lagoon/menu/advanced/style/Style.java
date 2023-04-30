package me.combimagnetron.lagoon.menu.advanced.style;

public interface Style {

    Style style(StyleFeature styleFeature);

    default StyleBuilder style() {
        return new StyleBuilder();
    }

    class StyleBuilder {



    }

}
