package com.example.order.api.dtos;

import com.example.order.domain.OrderedItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record OrderDto(String id, String customerId, Map<String, OrderedItem> orderMap, double totalPrice) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Double.compare(orderDto.totalPrice, totalPrice) == 0 && Objects.equals(customerId, orderDto.customerId) && Objects.equals(orderMap, orderDto.orderMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderMap, totalPrice);
    }
}
