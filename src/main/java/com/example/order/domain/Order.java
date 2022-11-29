package com.example.order.domain;

import com.example.order.domain.repos.UserRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final String customerId;
    private final Map<String, OrderedItem> itemGroup;
    private final double price;

    public Order(String customerId, Map<String, OrderedItem> itemGroup) {
        this.orderId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroup = itemGroup;
        this.price = calculatePrice();
    }

    public double getPrice() {
        return price;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Map<String, OrderedItem> getItemGroup() {
        return itemGroup;
    }
    public double calculatePrice(){
        double totalPrice = 0;
        for (OrderedItem item : itemGroup.values()) {
            totalPrice += item.getPrice() * item.getAmount();
        }
        return totalPrice;
    }
}
