package com.example.order.api;

import com.example.order.api.dtos.UserDto;
import com.example.order.domain.repos.UserRepo;
import com.example.order.domain.security.Role;
import com.example.order.services.mappers.UserMapper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
    //need to test -> registering user -> done
    //              -> getting list of all users
    //              -> getting single user

    @LocalServerPort
    int port;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;

    @DisplayName("user registration tests")
    @Nested
    public class CreateUserTests {
        @Test
        void CreatBook_WithValidFields_ReturnsBookDtoInJSON() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("firstName", "testName");
            requestParams.put("lastName", "TestName");
            requestParams.put("mailAddress", "unit.test@test.code");
            requestParams.put("phoneNumber", "04111111111");
            requestParams.put("passWord", "testpwd");

            UserDto result = RestAssured.given().port(port).log().all().contentType("application/json").body(requestParams)
                    .when().post("/users/register")
                    .then().statusCode(201).and().extract().as(UserDto.class);
            assertEquals(new UserDto(result.id(), "testName", "TestName", "unit.test@test.code",
                    "04111111111", null, Role.USER), result);
        }

        @Test
        void CreatBook_WithInValidFields_ReturnsErrorMessage() {
            JSONObject requestParams = new JSONObject();
            requestParams.put("firstName", "testNameFalse");
            requestParams.put("lastName", "");
            requestParams.put("mailAddress", "unit.test.code");
            requestParams.put("phoneNumber", "04111111112");
            requestParams.put("passWord", "testpwd");

            JSONObject result = RestAssured.given().port(port).log().all().contentType("application/json").body(requestParams)
                    .when().post("/users/register")
                    .then().statusCode(400).and().extract().as(JSONObject.class);
            assertEquals("Incorrect user input in field:  last name | mail address |", result.get("message").toString());
        }
    }
    @DisplayName("get user or userList tests")
    @Nested
    public class GetUserTests {
        @Test
        void getUserByID_withValidIdAsAdmin_returnsCorrectUserDto(){
            UserDto result = RestAssured.given().port(port).auth().preemptive().basic("admin@test.code", "pwd")
                    .when().get("/users/Casper")
                    .then().statusCode(200).and().extract().as(UserDto.class);
            assertEquals(userMapper.toDto(userRepo.getUserMap().get("Casper")), result);
        }
        @Test
        void getAllUsers_withValidIdAsAdmin_returnsCorrectUserDtoList(){
            List<UserDto> result = RestAssured.given().port(port).auth().preemptive().basic("admin@test.code", "pwd")
                    .when().get("/users")
                    .then().statusCode(200).and().extract().as(new TypeRef<List<UserDto>>() {
                    });
            assertEquals(userMapper.toDto(userRepo.getAllUsers()), result);
        }
    }
}
