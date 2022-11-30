package com.example.order.api.dtos;

public record UpdateItemDto(String name, String description, double price, int amount) {
}
