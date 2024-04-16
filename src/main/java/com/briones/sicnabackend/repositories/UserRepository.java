package com.briones.sicnabackend.repositories;

import com.briones.sicnabackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPersonRegistrationNumber(String registrationNumber);
}

