package com.briones.sicnabackend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.briones.sicnabackend.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstName(String firstName);
}
