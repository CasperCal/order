package com.example.order.domain.repos;

import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.domain.OrderedItem;
import com.example.order.domain.User;
import com.example.order.domain.security.Role;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderRepo {
    private final Map<String, Order> orderMap = new HashMap<>(
    );

    public OrderRepo() {}

    public Order save(Order order) {
        orderMap.put(order.getOrderId(), order);
        return order;
    }

//    public Optional<Order> getOrderById(String id) {
//        return Optional.ofNullable(orderMap.get(id));
//    }
//
    public List<Order> getAllOrders() { return orderMap.values().stream().toList();}

}
