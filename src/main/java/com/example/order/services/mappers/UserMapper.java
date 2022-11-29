package com.example.order.services.mappers;

import com.example.order.api.dtos.UserDto;
import com.example.order.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getMailAddress(), user.getPhoneNumber(), user.getShoppingList(), user.getRole());
    }

}
