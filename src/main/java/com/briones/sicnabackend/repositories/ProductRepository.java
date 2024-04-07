package com.briones.sicnabackend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.briones.sicnabackend.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
