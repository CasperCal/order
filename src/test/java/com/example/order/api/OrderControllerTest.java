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
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        @Test
        void givenAuthUser_AddingItemToShoppingListOneItemTwice_thenItemIsInList(){
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .with().queryParam("itemId","1")
                    .queryParam("amount", 1)
                    .when().post("/orders/add")
                    .then().statusCode(201);
            ItemDto result = RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .with().queryParam("itemId","1")
                    .queryParam("amount", 1)
                    .when().post("/orders/add")
                    .then().statusCode(201).and().extract().body().as(ItemDto.class);
            List<ItemDto> resultAsList = new ArrayList<>();
            resultAsList.add(result);
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
            expectedOrderMap.put("1", new OrderedItem("testItem1", "an item for tests", 0.5, 1));
            assertEquals(new OrderDto(result.id(), "Casper", expectedOrderMap, 0.5),
                        result);
        }
        @Test
        void givenAuthUserWithShoppingListWithSingleItemNotInStock_whenCheckout_ListConvertedToOrders() {
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .with().queryParam("itemId", "2")
                    .queryParam("amount", 100)
                    .when().post("/orders/add")
                    .then().statusCode(201);
            OrderDto result = RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .when().post("/orders/checkout")
                    .then().statusCode(201).and().extract().body().as(OrderDto.class);
            Map<String, OrderedItem> expectedOrderMap = new HashMap<>();
            OrderedItem expectedOrderItem = new OrderedItem("testItem2", "another item for tests", 12, 100);
            expectedOrderItem.setDeliveryDate(LocalDate.now().plusDays(7));
            expectedOrderMap.put("2", expectedOrderItem);
            assertEquals(new OrderDto(result.id(), "Casper", expectedOrderMap, 1200),
                    result);
        }
        @Test
        void givenAuthUserWithShoppingListWithSingleItemAddedTwice_whenCheckout_ListConvertedToOrders() {
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .with().queryParam("itemId", "1")
                    .queryParam("amount", 1)
                    .when().post("/orders/add")
                    .then().statusCode(201);
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .with().queryParam("itemId", "1")
                    .queryParam("amount", 1)
                    .when().post("/orders/add")
                    .then().statusCode(201);
            OrderDto result = RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .when().post("/orders/checkout")
                    .then().statusCode(201).and().extract().body().as(OrderDto.class);
            Map<String, OrderedItem> expectedOrderMap = new HashMap<>();
            expectedOrderMap.put("1", new OrderedItem("testItem1", "an item for tests", 0.5, 2));
            assertEquals(new OrderDto(result.id(), "Casper", expectedOrderMap, 1),
                    result);
        }
    }
    @DisplayName("test regarding getting individual customer history")
    @Nested
    class historyTest {
        @Test
        void givenCustomer_WhenPlacingOrderAndGetHistory_ReturnsOrderHistory() {
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .with().queryParam("itemId", "1")
                    .queryParam("amount", 1)
                    .when().post("/orders/add")
                    .then().statusCode(201);
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .with().queryParam("itemId", "2")
                    .queryParam("amount", 50)
                    .when().post("/orders/add")
                    .then().statusCode(201);
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .when().post("/orders/checkout")
                    .then().statusCode(201);
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .with().queryParam("itemId", "1")
                    .queryParam("amount", 10)
                    .when().post("/orders/add")
                    .then().statusCode(201);
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .with().queryParam("itemId", "1")
                    .queryParam("amount", 5)
                    .when().post("/orders/add")
                    .then().statusCode(201);
            RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .when().post("/orders/checkout")
                    .then().statusCode(201);
            List<OrderDto> result = RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .log().all().contentType("application/json")
                    .when().get("/orders/history")
                    .then().statusCode(200).and().extract().as(new TypeRef<List<OrderDto>>() {
                    });
            assertEquals(result.size(), 2);
            Map<String, OrderedItem> expectedOrderMap = new HashMap<>();
            expectedOrderMap.put("1", new OrderedItem("testItem1", "an item for tests", 0.5, 15));
            assertEquals(new OrderDto(result.get(1).id(), "Casper", expectedOrderMap, 7.5),
                    result.get(1));
        }
    }
}
