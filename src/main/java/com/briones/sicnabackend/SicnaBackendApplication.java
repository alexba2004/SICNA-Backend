package com.briones.sicnabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.briones.sicnabackend", "com.briones.lib.seeders", "com.briones.controllers", "com.briones.config"}, exclude = {SecurityAutoConfiguration.class})
public class SicnaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicnaBackendApplication.class, args);
	}

}
