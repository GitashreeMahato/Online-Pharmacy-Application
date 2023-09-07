package com.jsp.onlinepharmacy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlinepharmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinepharmacyApplication.class, args);
	}
	// modelmapper is a class
	// ask spring to tell to create the object for modelmapper(third party class)
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
