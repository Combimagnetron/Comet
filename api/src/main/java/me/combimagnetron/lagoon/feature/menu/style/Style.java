package me.combimagnetron.lagoon.feature.menu.style;

public interface Style {

    Style style(StyleFeature styleFeature);

    static StyleBuilder style() {
        return new StyleBuilder();
    }

    class StyleBuilder {



    }

}
