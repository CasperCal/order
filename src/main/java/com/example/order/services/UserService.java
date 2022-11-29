package com.example.order.services;

import com.example.order.api.dtos.CreateUserDto;
import com.example.order.api.dtos.UserDto;
import com.example.order.domain.Item;
import com.example.order.domain.User;
import com.example.order.domain.repos.UserRepo;
import com.example.order.services.mappers.UserMapper;
import com.example.order.services.regex.RegexHelper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public UserDto createUser(CreateUserDto createUserDto) {
        String error = validateUserInput(createUserDto);
        if (!error.isEmpty()) {throw new IllegalArgumentException("Incorrect user input in field: "+ error);}
        User user = new User(createUserDto.firstName(), createUserDto.lastName(), createUserDto.mailAddress(), createUserDto.phoneNumber(), createUserDto.passWord());
        return userMapper.toDto(userRepo.save(user));
    }

    private String validateUserInput(CreateUserDto createUserDto) {
        String errorMessage = "";
        if (createUserDto.firstName().isEmpty()) {errorMessage += " first name |";}
        if (createUserDto.lastName().isEmpty()) {errorMessage += " last name |";}
        if (!RegexHelper.checkMail(createUserDto.mailAddress())) {errorMessage += " mail address |";}
        if (createUserDto.phoneNumber().isEmpty()) {errorMessage += " phone number |";}
        if (createUserDto.passWord().isEmpty()) {errorMessage += " password";}
        return errorMessage;
    }

}
