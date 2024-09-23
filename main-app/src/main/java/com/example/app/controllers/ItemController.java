package com.example.app.controllers;

import com.example.app.dto.ItemUpdateDto;
import com.example.app.entities.ItemEntity;
import com.example.app.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable UUID uuid) {
        return new ResponseEntity<>(itemService.getItemById(uuid), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemEntity>> getAll() {
        return new ResponseEntity<>(itemService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemEntity> saveOneItem(@RequestBody @Valid ItemEntity itemEntity) {
        return new ResponseEntity<>(itemService.saveOneItem(itemEntity), HttpStatus.CREATED);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<ItemEntity>> saveItemList(@RequestBody @Valid List<ItemEntity> itemEntityList) {
        return new ResponseEntity<>(itemService.saveAllItems(itemEntityList), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ItemEntity> updateItem(@PathVariable UUID uuid, @RequestBody ItemUpdateDto itemUpdateDto) {
        return new ResponseEntity<>(itemService.updateItem(uuid, itemUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ItemEntity> updateItem(@PathVariable UUID uuid) {
        itemService.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
