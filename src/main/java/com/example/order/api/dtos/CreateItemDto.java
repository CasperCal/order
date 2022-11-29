package com.example.order.api.dtos;

public record CreateItemDto(String name, String description, double price, int amount) {
}
