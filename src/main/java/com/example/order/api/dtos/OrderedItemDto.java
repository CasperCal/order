package com.example.order.api.dtos;

import java.time.LocalDate;

public record OrderedItemDto(String id, String name, String description, double price, int amount, LocalDate shippingDate) {
}
