package com.example.order.api.dtos;

import com.example.order.domain.Order;
import com.example.order.domain.security.Role;

import java.util.List;

public record CreateUserDto(String firstName, String lastName, String mailAddress, String phoneNumber, List<Order> orderList, String passWord) {
}
