package com.practicemovieapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PracticeMovieApplication {

	public static void main(String[] args) {

		SpringApplication.run(PracticeMovieApplication.class, args);
	}

}
