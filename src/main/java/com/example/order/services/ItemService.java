package com.example.order.services;

import com.example.order.api.dtos.CreateItemDto;
import com.example.order.api.dtos.ItemDto;
import com.example.order.domain.Item;
import com.example.order.domain.repos.ItemRepo;
import com.example.order.services.mappers.ItemMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepo itemRepo;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepo itemRepo, ItemMapper itemMapper) {
        this.itemRepo = itemRepo;
        this.itemMapper = itemMapper;
    }

    public ItemDto createItem(CreateItemDto createItemDto) {
        String error = validateItemInput(createItemDto);
        if (!error.isEmpty()) {throw new IllegalArgumentException("Incorrect item input in field: " + error);}
        Item item = new Item(createItemDto.name(), createItemDto.description(), createItemDto.price(), createItemDto.amount());
        return itemMapper.toDto(itemRepo.save(item));
    }

    private String validateItemInput(CreateItemDto createItemDto) {
        String errorMessage = "";
        if (createItemDto.price() <= 0) {errorMessage += " price |";}
        if (createItemDto.amount() < 0) {errorMessage += " amount |";}
        if (createItemDto.name().isEmpty()) {errorMessage += " name |";}
        if (createItemDto.description().isEmpty()) {errorMessage += " description |";}
        return errorMessage;
    }
}
