package com.example.order.services.mappers;

import com.example.order.api.dtos.ItemDto;
import com.example.order.api.dtos.OrderDto;
import com.example.order.domain.Item;
import com.example.order.domain.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderMapper {
    public OrderDto toDto(Order order) {
        return new OrderDto(order.getOrderId(), order.getCustomerId(), order.getItemGroup(), order.getPrice());
    }
}
