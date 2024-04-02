package com.briones.sicnabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.briones.sicnabackend.models")
@EnableJpaRepositories("com.briones.sicnabackend.repositories")
public class SicnaBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SicnaBackendApplication.class, args);
    }
}
