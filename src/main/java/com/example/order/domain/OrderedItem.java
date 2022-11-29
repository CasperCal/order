package com.example.order.domain;

import java.time.LocalDate;

public class OrderedItem extends Item{
    private final static int SHIPPING_DELTA_IN_STOCK = 1;
    private final static int SHIPPING_DELTA_NOT_IN_STOCK = 7;
    private LocalDate deliveryDate;

    public OrderedItem(String name, String description, double price, int amount) {
        super(name, description, price, amount);
        this.deliveryDate = calculateShippingDate();
    }

    private LocalDate calculateShippingDate() {
        if (super.getAmount() == 0) {return LocalDate.now().plusDays(SHIPPING_DELTA_NOT_IN_STOCK);}
        return LocalDate.now().plusDays(SHIPPING_DELTA_IN_STOCK);
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
}

