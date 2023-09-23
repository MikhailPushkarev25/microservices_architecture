package com.programmingtechie.inventoryservice.controller;

import com.programmingtechie.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam(name = "sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
