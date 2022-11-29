package com.example.order.domain;

import com.example.order.domain.security.Feature;
import com.example.order.domain.security.Role;

import java.util.List;
import java.util.UUID;

public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String mailAddress;
    private final String phoneNumber;
    private List<Order> orderList;
    private final Role role;
    private final String passWord;

    public User(String firstName, String lastName, String mailAddress, String phoneNumber, String passWord) {
        this.role = Role.USER;
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }

    public boolean doesPasswordMatch(String password) {
        return this.passWord.equals(password);
    }

    public boolean hasAccessTo(Feature feature) {
        return this.role.hasFeature(feature);
    }

    public User(String firstName, String lastName, String mailAddress, String phoneNumber, List<Order> orderList, String passWord) {
        this.role = Role.USER;
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }
    public User(String firstName, String lastName, String mailAddress, String phoneNumber, Role role, String passWord) {
        this.role = role;
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }

    public User(String firstName, String lastName, String mailAddress, String phoneNumber, List<Order> orderList, Role role, String passWord) {
        this.role = role;
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Role getRole() {
        return role;
    }
}
