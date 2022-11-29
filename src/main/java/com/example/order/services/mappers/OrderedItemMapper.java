package com.example.order.services.mappers;

import com.example.order.api.dtos.ItemDto;
import com.example.order.api.dtos.OrderedItemDto;
import com.example.order.domain.Item;
import com.example.order.domain.OrderedItem;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class OrderedItemMapper {
    public OrderedItemDto toDto(OrderedItem item) {
        return new OrderedItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmount(), item.getDeliveryDate());
    }
}
