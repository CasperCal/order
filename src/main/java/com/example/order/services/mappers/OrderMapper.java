package com.example.order.services.mappers;

import com.example.order.api.dtos.ItemDto;
import com.example.order.api.dtos.OrderDto;
import com.example.order.api.dtos.UserDto;
import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderMapper {
    public OrderDto toDto(Order order) {
        return new OrderDto(order.getOrderId(), order.getCustomerId(), order.getItemGroup(), order.getPrice());
    }
    public List<OrderDto> toDto(List<Order> orders){
        return orders.stream()
                .map(this::toDto)
                .toList();
    }
}
