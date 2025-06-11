package com.finplanner.finplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinPlannerApplication.class, args);
	}

}
