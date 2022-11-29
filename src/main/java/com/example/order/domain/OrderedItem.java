package com.example.order.domain;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderedItem that = (OrderedItem) o;
        return Objects.equals(deliveryDate, that.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), deliveryDate);
    }
}

