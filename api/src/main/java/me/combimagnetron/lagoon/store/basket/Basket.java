package me.combimagnetron.lagoon.store.basket;

import me.combimagnetron.lagoon.store.parcel.Parcel;

import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public class Basket {
    private final Set<Parcel> parcels = new HashSet<>();
    private Currency currency;
    private double total = 0.00;



}
