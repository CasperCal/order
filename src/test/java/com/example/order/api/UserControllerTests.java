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
import org.springframework.test.annotation.DirtiesContext;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserControllerTests {

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
        void getUserByID_withValidIdAsAdmin_returnsCorrectUserDto() {
            UserDto result = RestAssured.given().port(port).auth().preemptive().basic("admin@test.code", "pwd")
                    .when().get("/users/Casper")
                    .then().statusCode(200).and().extract().as(UserDto.class);
            assertEquals(userMapper.toDto(userRepo.getUserMap().get("Casper")), result);
        }

        @Test
        void getUserByID_withInValidIdAsAdmin_returnsCorrectUserDto() {
            JSONObject result = RestAssured.given().port(port).auth().preemptive().basic("admin@test.code", "pwd")
                    .when().get("/users/0")
                    .then().statusCode(404).and().extract().as(JSONObject.class);
            assertEquals("No User with ID: 0 found in repo.", result.get("message").toString());
        }

        @Test
        void getAllUsers_withValidIdAsAdmin_returnsCorrectUserDtoList() {
            List<UserDto> result = RestAssured.given().port(port).auth().preemptive().basic("admin@test.code", "pwd")
                    .when().get("/users")
                    .then().statusCode(200).and().extract().as(new TypeRef<List<UserDto>>() {
                    });
            assertEquals(userMapper.toDto(userRepo.getAllUsers()), result);
        }


        @Test
        void getAllUsers_withValidIdAsAdminWrongPassWord_throwsError() {
            JSONObject result = RestAssured.given().port(port).auth().preemptive().basic("admin@test.code", "oogabooga")
                    .when().get("/users")
                    .then().statusCode(403).and().extract().as(JSONObject.class);
            assertEquals("Wrong username or password.", result.get("message").toString());
        }

        @Test
        void getAllUsers_withValidIdAsUser_throwsError() {
            JSONObject result = RestAssured.given().port(port).auth().preemptive().basic("casper@test.code", "pwd")
                    .when().get("/users")
                    .then().statusCode(403).and().extract().as(JSONObject.class);
            assertEquals("Unauthorized", result.get("message").toString());
        }
    }
    @Test
    void getAllUsers_withValidInvalidLogin_throwsError() {
        JSONObject result = RestAssured.given().port(port).auth().preemptive().basic("oogabooga@test.code", "oogabooga")
                .when().get("/users")
                .then().statusCode(403).and().extract().as(JSONObject.class);
        assertEquals("Wrong username or password.", result.get("message").toString());
    }

    @Test
    void getAllUsers_withNoneSensePath_throwsError() {
        JSONObject result = RestAssured.given().port(port).auth().preemptive().basic("admin@test.code", "pwd")
                .when().get("/user")
                .then().statusCode(404).and().extract().as(JSONObject.class);
        assertEquals("Not Found", result.get("error").toString());
    }
}
