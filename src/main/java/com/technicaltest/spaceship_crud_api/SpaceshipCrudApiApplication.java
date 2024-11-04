package com.technicaltest.spaceship_crud_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class SpaceshipCrudApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceshipCrudApiApplication.class, args);
	}

}
