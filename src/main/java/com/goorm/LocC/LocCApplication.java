package com.goorm.LocC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LocCApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocCApplication.class, args);
	}

}
