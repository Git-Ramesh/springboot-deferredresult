package com.rs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootDeferredresultApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDeferredresultApplication.class, args);
	}

}
