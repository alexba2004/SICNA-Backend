package com.briones.sicnabackend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.briones.sicnabackend.models.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    Optional<Space> findByName(String name);
}
