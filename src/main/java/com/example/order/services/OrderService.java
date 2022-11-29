package com.example.order.services;

import com.example.order.api.dtos.ItemDto;
import com.example.order.api.dtos.OrderDto;
import com.example.order.domain.Order;
import com.example.order.domain.OrderedItem;
import com.example.order.domain.User;
import com.example.order.domain.repos.ItemRepo;
import com.example.order.domain.repos.OrderRepo;
import com.example.order.domain.repos.UserRepo;
import com.example.order.services.mappers.ItemMapper;
import com.example.order.services.mappers.OrderMapper;
import com.example.order.services.mappers.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final ItemRepo itemRepo;
    private final ItemMapper itemMapper;
    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;
    private User user;


    public OrderService(UserRepo userRepo, UserMapper userMapper, ItemRepo itemRepo, ItemMapper itemMapper, OrderMapper orderMapper, OrderRepo orderRepo) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.itemRepo = itemRepo;
        this.itemMapper = itemMapper;
        this.orderMapper = orderMapper;
        this.orderRepo = orderRepo;
    }

    public ItemDto addToShoppingList(String authorization, String itemId, int amount) {
        itemRepo.getItemById(itemId).orElseThrow(() -> new NoSuchElementException("Item not found")).setAmountDelta(-amount);
        return itemMapper.toDto(getUserByAuthorization(authorization).addToShoppingList(itemRepo.getItemById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found")), amount));
    }

    public OrderDto order(String authorization) {
        Order order = new Order(getUserByAuthorization(authorization).getId(), getUserByAuthorization(authorization).convertToOrder());
        checkStock(order.getItemGroup());
        return orderMapper.toDto(orderRepo.save(order));
    }

    private void checkStock(Map<String, OrderedItem> orderMap){
        for (OrderedItem item : orderMap.values()) {
            if(itemRepo.getItemById(item.getId()).orElseThrow(() -> new NoSuchElementException("Item not found"))
                    .getAmount() <= 0) {
                item.lateShipping();
            }
        }
    }

    private User getUserByAuthorization(String authorization) {
        String decodedToUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String userEmail = decodedToUsernameAndPassword.split(":")[0];
        return userRepo.getUserByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public boolean inStock(String itemId, int amount) {
        if(itemRepo.getItemById(itemId).orElseThrow(() ->new NoSuchElementException("Item not found")).getAmount() < amount)
            return false;
        return true;
    }
}
