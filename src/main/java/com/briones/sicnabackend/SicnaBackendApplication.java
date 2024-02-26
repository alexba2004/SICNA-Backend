package com.briones.sicnabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.briones.sicnabackend", "com.briones.lib.seeders"})
public class SicnaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicnaBackendApplication.class, args);
	}

}
