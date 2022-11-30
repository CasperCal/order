package com.example.order.api;

import com.example.order.api.dtos.ItemDto;
import com.example.order.domain.repos.ItemRepo;
import io.restassured.RestAssured;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTests {
    @LocalServerPort
    int port;

    @Autowired
    private ItemRepo itemrepo;

    @DisplayName("adding item tests")
    @Nested
    class AddItemTests{
        @Test
        void createItem_asAdminWithCorrectBody_ReturnsCorrectItemDto(){
            JSONObject requestParams = new JSONObject();
            requestParams.put("name","Unit test item");
            requestParams.put("description", "an item generated in unit tests");
            requestParams.put("price", 20.4);
            requestParams.put("amount", 1);

            ItemDto result = RestAssured.given().port(port).auth().preemptive().basic("admin@test.code","pwd")
                    .contentType("application/json").body(requestParams)
                    .when().post("/items/add")
                    .then().statusCode(201).and().extract().as(ItemDto.class);
            assertEquals(new ItemDto(result.id(),"Unit test item", "an item generated in unit tests",20.4,1)
                        , result);
        }
    }
}
