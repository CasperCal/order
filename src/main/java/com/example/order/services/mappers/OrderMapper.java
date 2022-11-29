package com.example.order.services.mappers;

import com.example.order.api.dtos.OrderDto;
import com.example.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDto toDto(Order order) {
        return new OrderDto(order.getOrderId(), order.getCustomerId(), order.getItemGroup(), order.getPrice());
    }
}
