package com.example.order.services.mappers;

import com.example.order.api.dtos.ItemDto;
import com.example.order.api.dtos.UserDto;
import com.example.order.domain.Item;
import com.example.order.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {
    public ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmount());
    }

    public List<ItemDto> toDto(List<Item> allItems){
        return allItems.stream()
                .map(this::toDto)
                .toList();
    }
}
