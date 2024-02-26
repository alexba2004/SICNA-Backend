package com.briones.sicnabackend.repositories;

import com.briones.sicnabackend.models.InventoryItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

}
