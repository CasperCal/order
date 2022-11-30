package com.example.order.services.mappers;

import com.example.order.api.dtos.ItemDto;
import com.example.order.api.dtos.OrderDto;
import com.example.order.api.dtos.OrderedItemDto;
import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.domain.OrderedItem;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderedItemMapper {
    public OrderedItemDto toDto(OrderedItem item) {
        return new OrderedItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmount(), item.getDeliveryDate());
    }
    public List<OrderedItemDto> toDto(List<OrderedItem> allItems){
        return allItems.stream()
                .map(this::toDto)
                .toList();
    }

    public List<OrderedItemDto> toDto(Map<String, OrderedItem> allItems){
        return allItems.values().stream()
                .map(this::toDto)
                .toList();
    }
}
