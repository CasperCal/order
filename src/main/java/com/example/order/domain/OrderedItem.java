package com.example.order.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;
import java.util.Objects;

public class OrderedItem extends Item{
    private final static int SHIPPING_DELTA_IN_STOCK = 1;
    private final static int SHIPPING_DELTA_NOT_IN_STOCK = 7;
    private LocalDate deliveryDate = LocalDate.now().plusDays(SHIPPING_DELTA_IN_STOCK);

    @JsonCreator
    public OrderedItem(String name, String description, double price, int amount) {
        super(name, description, price, amount);
    }

    public OrderedItem(String id, String name, String description, double price, int amount) {
        super(id, name, description, price, amount);
    }

    public void lateShipping() {
        this.deliveryDate =  LocalDate.now().plusDays(SHIPPING_DELTA_NOT_IN_STOCK);
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderedItem that = (OrderedItem) o;
        return Objects.equals(deliveryDate, that.deliveryDate);
    }
}

