package com.trading.platform.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.trading.platform.core")
public class CoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreServiceApplication.class, args);
	}

}
