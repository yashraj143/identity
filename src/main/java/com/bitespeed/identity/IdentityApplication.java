package com.bitespeed.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

@ComponentScan(
		basePackages = {
				"com.bitespeed.identity.repository",
				"com.bitespeed.identity.service",
				"com.bitespeed.identity.controller",
				"com.bitespeed.identity.model",
				"com.bitespeed.identity.enums",
				"com.bitespeed.identity.entity"
		})

public class IdentityApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityApplication.class, args);
	}

}
