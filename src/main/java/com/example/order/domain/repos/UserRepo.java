package com.example.order.domain.repos;

import com.example.order.api.UserController;
import com.example.order.domain.User;
import com.example.order.domain.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepo {
    Logger myLogger = LoggerFactory.getLogger(UserController.class);
    private final Map<String, User> userMap =  new HashMap<>();

    public UserRepo(){
        this.userMap.put("admin", new User("admin", "admin", "", "admin@test.code", "", Role.ADMIN, "pwd"));
        this.userMap.put("Casper", new User("Casper","Casper", "", "casper@test.code", "", Role.USER, "pwd"));
    }

    public User save(User user) throws IllegalArgumentException {
        if (userMap.containsValue(user)) throw new IllegalArgumentException("User already exists.");

        for (Map.Entry<String, User> user1 : userMap.entrySet()) {
            if (user.getMailAddress().equals(user1.getValue().getMailAddress())) {throw new IllegalArgumentException("E-Mail address already registered.");}
        }
        userMap.put(user.getId(), user);
        myLogger.info(user.getMailAddress() + " has created an account.");
        return user;
    }

    public Optional<User> getUserById(String userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    public Optional<User> getUserByEmail(String email) {
        return getAllUsers().stream()
                .filter(user -> user.getMailAddress().equals(email))
                .findFirst();
    }

    public List<User> getAllUsers() {return userMap.values().stream().toList();}

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
