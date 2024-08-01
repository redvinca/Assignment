package com.redvinca.assignment.ecom_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.redvinca.assignment.ecom_backend"})
public class EcomBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomBackendApplication.class, args);
	}

}
