package com.example.order.domain.repos;

import com.example.order.api.ItemController;
import com.example.order.domain.Item;
import com.example.order.domain.User;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemRepo {
    Logger myLogger = LoggerFactory.getLogger(ItemController.class);
    private final Map<String, Item> itemMap = new HashMap<>();

    public ItemRepo(){
        itemMap.put("1", new Item("1", "testitem", "an item for tests", 0.5, 100));
    }

    public Item save(Item item) throws IllegalArgumentException {
        if (itemMap.containsValue(item)) {throw new IllegalArgumentException("Item already exists. Use Update instead.");}
        for (Map.Entry<String, Item> item1 : itemMap.entrySet()) {
            if (item.getName().equals(item1.getValue().getName())) {throw new IllegalArgumentException("Itemname already registered. Chose another or use update.");}

        }
        itemMap.put(item.getId(), item);
        myLogger.info(item.getName() + "has been created as an item with ID: " + item.getId());
        return item;
    }

    public Optional<Item> getItemById(String itemId) {
        return Optional.ofNullable(itemMap.get(itemId));
    }
    public List<Item> getAllItems() {return itemMap.values().stream().toList();}
}
