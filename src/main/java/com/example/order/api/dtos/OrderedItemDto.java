package com.example.order.api.dtos;


import org.springframework.stereotype.Service;

import java.time.LocalDate;


public record OrderedItemDto(String id, String name, String description, double price, int Amount, LocalDate deliveryDate) {
}
