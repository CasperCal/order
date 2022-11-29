package com.example.order.api.dtos;

import com.example.order.domain.OrderedItem;

import java.util.Map;

public record OrderDto(String id, String customerId, Map<String, OrderedItem> orderMap, double totalPrice) {
}
