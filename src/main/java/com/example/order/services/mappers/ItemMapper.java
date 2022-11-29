package com.example.order.services.mappers;

import com.example.order.api.dtos.ItemDto;
import com.example.order.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmount());
    }
}
