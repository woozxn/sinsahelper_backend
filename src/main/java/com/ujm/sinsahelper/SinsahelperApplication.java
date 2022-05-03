package com.ujm.sinsahelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SinsahelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SinsahelperApplication.class, args);
	}

}
