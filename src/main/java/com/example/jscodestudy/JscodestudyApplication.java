package com.example.jscodestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JscodestudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JscodestudyApplication.class, args);
	}

}
