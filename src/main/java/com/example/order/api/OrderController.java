package com.example.order.api;

import com.example.order.api.dtos.ItemDto;
import com.example.order.api.dtos.OrderDto;
import com.example.order.domain.security.Feature;
import com.example.order.services.OrderService;
import com.example.order.services.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="orders")
public class OrderController {
    SecurityService securityService;
    OrderService orderService;

    public OrderController(SecurityService securityService, OrderService orderService) {
        this.securityService = securityService;
        this.orderService = orderService;
    }

    @PostMapping(path="add", params = {"itemId", "amount"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addToShoppingList(@RequestParam String itemId, @RequestParam int amount, @RequestHeader String authorization) {
        securityService.validateAuthorisation(authorization, Feature.ADD_TO_SHOPPING);
        return orderService.addToShoppingList(authorization, itemId, amount);
    }
    @PostMapping(path = "checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto checkoutShoppingList(@RequestHeader String authorization) {
        securityService.validateAuthorisation(authorization, Feature.ORDER_ITEM);
        return orderService.order(authorization);
    }
    @GetMapping(path = "history", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> viewOrderHistory(@RequestHeader String authorization) {
        securityService.validateAuthorisation(authorization, Feature.VIEW_ORDERS);
        return orderService.orderOverView(authorization);
    }

    @PostMapping(path = "repeat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto repeatOrder(@RequestHeader String authorization, @PathVariable String id) {
        securityService.validateAuthorisation(authorization, Feature.REORDER);
        return orderService.repeatOrder(authorization, id);
    }

}
