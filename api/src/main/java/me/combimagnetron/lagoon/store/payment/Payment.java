package me.combimagnetron.lagoon.store.payment;

public record Payment() {

    record Response(String paymentMethod, boolean success) {
        
    }

}
