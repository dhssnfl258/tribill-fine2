package com.capstone.tribillfine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
@EntityScan
@SpringBootApplication
public class TribillFineApplication {
	public static void main(String[] args) {
		SpringApplication.run(TribillFineApplication.class, args);
	}

}
