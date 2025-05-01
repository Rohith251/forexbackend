package com.forexcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ForexCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForexCardApplication.class, args);
	}

}
