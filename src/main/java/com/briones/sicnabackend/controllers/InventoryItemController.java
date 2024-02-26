package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.models.InventoryItem;
import com.briones.sicnabackend.repositories.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory-items")
public class InventoryItemController {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        return ResponseEntity.ok(inventoryItems);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable Long itemId) {
        Optional<InventoryItem> inventoryItemOptional = inventoryItemRepository.findById(itemId);
        return inventoryItemOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventoryItem> createInventoryItem(@RequestBody InventoryItem inventoryItem) {
        InventoryItem createdInventoryItem = inventoryItemRepository.save(inventoryItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventoryItem);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<InventoryItem> updateInventoryItem(@PathVariable Long itemId, @RequestBody InventoryItem updatedInventoryItem) {
        Optional<InventoryItem> inventoryItemOptional = inventoryItemRepository.findById(itemId);
        if (inventoryItemOptional.isPresent()) {
            updatedInventoryItem.setId(inventoryItemOptional.get().getId());
            InventoryItem savedInventoryItem = inventoryItemRepository.save(updatedInventoryItem);
            return ResponseEntity.ok(savedInventoryItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long itemId) {
        Optional<InventoryItem> inventoryItemOptional = inventoryItemRepository.findById(itemId);
        if (inventoryItemOptional.isPresent()) {
            inventoryItemRepository.delete(inventoryItemOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
