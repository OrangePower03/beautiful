package com.example.java;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.example.java.mapper"})
@SpringBootApplication
public class JavaApplication {

	public static void main(String[] args) {

		SpringApplication.run(JavaApplication.class, args);
	}

}


