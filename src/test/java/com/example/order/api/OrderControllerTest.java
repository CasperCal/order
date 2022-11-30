package com.example.order.api;


import com.example.order.api.dtos.ItemDto;
import com.example.order.api.dtos.OrderDto;
import com.example.order.domain.OrderedItem;
import com.example.order.domain.exceptions.UnknownUserException;
import com.example.order.domain.repos.ItemRepo;
import com.example.order.domain.repos.OrderRepo;
import com.example.order.domain.repos.UserRepo;
import com.example.order.services.mappers.ItemMapper;
import com.example.order.services.mappers.UserMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class OrderControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ItemMapper itemMapper;

    @DisplayName("test regarding adding ordered items to shoppingList")
    @Nested
    class orderedItemTests {
        @Test
        void givenAuthUser_AddingItemToShoppingListOneItem_thenItemIsInList(){
            ItemDto result = RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .with().queryParam("itemId","1")
                    .queryParam("amount", 1)
                    .when().post("/orders/add")
                    .then().statusCode(201).and().extract().body().as(ItemDto.class);
            List<ItemDto> resultAsList = new ArrayList<>();
            resultAsList.add(result);
            System.out.println(result);
            assertEquals(itemMapper.toDto(userRepo.getUserByEmail("casper@test.code").orElseThrow(UnknownUserException::new).getShoppingList())
                        , resultAsList);
        }
    }

    @DisplayName("test regarding checking out shoppingList to Order")
    @Nested
    class orderTest {
        @Test
        void givenAuthUserWithShoppingListWithSingleItem_whenCheckout_ListConvertedToOrders() {
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .with().queryParam("itemId", "1")
                    .queryParam("amount", 1)
                    .when().post("/orders/add")
                    .then().statusCode(201).and().extract().body().as(ItemDto.class);
            OrderDto result = RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .when().post("/orders/checkout")
                    .then().statusCode(201).and().extract().body().as(OrderDto.class);
            Map<String, OrderedItem> expectedOrderMap = new HashMap<>();
            expectedOrderMap.put("1", new OrderedItem("testItem1", "an item for tests", 0.5, 100));
            assertEquals(new OrderDto(result.id(), "Casper", expectedOrderMap, 0.5),
                        result);
        }
    }
}
