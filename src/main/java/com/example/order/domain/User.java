package com.example.order.domain;

import com.example.order.domain.security.Feature;
import com.example.order.domain.security.Role;

import java.util.*;

public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String mailAddress;
    private final String phoneNumber;
    private List<Item> shoppingList = new ArrayList<>();
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

    public Item addToShoppingList(Item item, int amount){
        Item shoppingItem = new Item(item.getName(), item.getDescription(), item.getPrice(), amount);
        shoppingList.add(shoppingItem);
        return shoppingItem;

    }
    public Map<String, OrderedItem> convertToOrder() {
        Map<String, OrderedItem> orderMap = new HashMap<>();
        for (Item item : shoppingList){
            orderMap.put(UUID.randomUUID().toString(), (new OrderedItem(item.getName(), item.getDescription(), item.getPrice(), item.getAmount())));
        }
        shoppingList = new ArrayList<>();
        return orderMap;
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

    public List<Item> getShoppingList() {
        return shoppingList;
    }

    public Role getRole() {
        return role;
    }
}
