package com.example.order.api;

import com.example.order.api.dtos.CreateItemDto;
import com.example.order.api.dtos.ItemDto;
import com.example.order.domain.security.Feature;
import com.example.order.services.ItemService;
import com.example.order.services.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "items")
public class ItemController {
    private final ItemService itemService;
    private final SecurityService securityService;

    public ItemController(ItemService itemService, SecurityService securityService) {
        this.itemService = itemService;
        this.securityService = securityService;
    }

    @PostMapping(path = "add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createUser(@RequestBody CreateItemDto createItemDto, @RequestHeader String authorization) {
        securityService.validateAuthorisation(authorization, Feature.ADD_ITEM);
        return itemService.createItem(createItemDto);
    }
}
