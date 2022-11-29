package com.example.order.api.dtos;

import com.example.order.domain.Order;
import com.example.order.domain.security.Role;

import java.util.List;

public record UserDto(String id, String firstName, String LastName, String mailAddress, String phoneNumber, List<Order> orderList, Role role) {
}
