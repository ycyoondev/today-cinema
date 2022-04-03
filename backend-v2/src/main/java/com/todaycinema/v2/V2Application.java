package com.todaycinema.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.todaycinema.v2")
public class V2Application {

	public static void main(String[] args) {
		SpringApplication.run(V2Application.class, args);
	}

}
