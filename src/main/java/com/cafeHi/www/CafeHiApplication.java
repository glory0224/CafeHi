package com.cafeHi.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = {"com.cafeHi.www.mapper"})
@SpringBootApplication
public class CafeHiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeHiApplication.class, args);
	}

}
