package com.example.order.api.dtos;

public record ItemDto(String id, String name, String description, double price, int amount) {
}
