package com.cafeHi.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CafeHiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeHiApplication.class, args);
	}

}
