package com.example.order.api;

import com.example.order.api.dtos.CreateUserDto;
import com.example.order.api.dtos.UserDto;
import com.example.order.domain.security.Feature;
import com.example.order.services.SecurityService;
import com.example.order.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "users")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getAllUsers(@RequestHeader String authorization) {
        securityService.validateAuthorisation(authorization, Feature.VIEW_ALL_USERS);
        return userService.getAllUsers();
    }
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUserById(@RequestHeader String authorization, @PathVariable String id){
        securityService.validateAuthorisation(authorization, Feature.VIEW_SINGLE_USER);
        return userService.getUserById(id);
    }
    @PostMapping(path = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody CreateUserDto createUserDto) {
        return userService.createUser(createUserDto);
    }
}
