package me.combimagnetron.lagoon.store.parcel;

import java.util.Currency;

public interface Parcel {

    double price(Currency currency);

    default double defaultPrice() {
        return price(Currency.getInstance("USD"));
    }

}
