package com.briones.sicnabackend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.briones.sicnabackend.models.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByName(String name);
}
