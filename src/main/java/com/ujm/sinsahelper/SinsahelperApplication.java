package com.ujm.sinsahelper;

import com.ujm.sinsahelper.config.properties.AppProperties;
import com.ujm.sinsahelper.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class SinsahelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SinsahelperApplication.class, args);
	}

}
