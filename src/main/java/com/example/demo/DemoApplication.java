package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(R2dbcEntityTemplate entityTemplate) {
		return args -> {
			entityTemplate.getDatabaseClient().sql(
					"CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, firstName VARCHAR(100) NOT NULL,lastName VARCHAR(100) , email VARCHAR(100))")
					.fetch().rowsUpdated().block();
			entityTemplate.insert(new User(null, "John Doe", "", "john.doe@example.com")).block();
			entityTemplate.insert(new User(null, "Jane Doe", "", "jane.doe@example.com")).block();
		};
	}
}
