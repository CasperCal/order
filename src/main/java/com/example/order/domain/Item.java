package com.example.order.domain;

import java.util.UUID;

public class Item {
    private final String id;
    private final String name;
    private final String description;
    private final double price;
    private final int stockAmount;

    public Item(String name, String description, double price, int stockAmount) {
        if (stockAmount < 0 || price <= 0) {throw new IllegalArgumentException("value can't be that low.");}
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockAmount = stockAmount;
    }

}
