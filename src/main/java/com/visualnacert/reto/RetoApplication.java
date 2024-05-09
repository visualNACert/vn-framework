package com.visualnacert.reto;

import com.visual.framework.exceptions.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalExceptionHandler.class)
public class RetoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetoApplication.class, args);
	}

}
