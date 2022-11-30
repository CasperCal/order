package com.example.order.api.dtos;

import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.domain.security.Role;

import java.util.List;
import java.util.Objects;

public record UserDto(String id, String firstName, String LastName, String mailAddress, String phoneNumber, List<Item> shoppingList, Role role) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(firstName, userDto.firstName) && Objects.equals(LastName, userDto.LastName) && Objects.equals(mailAddress, userDto.mailAddress) && Objects.equals(phoneNumber, userDto.phoneNumber) && role == userDto.role;
    }
}
