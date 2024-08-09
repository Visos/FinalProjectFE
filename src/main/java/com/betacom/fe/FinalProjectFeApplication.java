package com.betacom.fe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FinalProjectFeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectFeApplication.class, args);
	}

	
	@Bean
	public RestTemplate rest() {
		RestTemplate rt = new RestTemplateBuilder().build();
		return rt;
	}
}
