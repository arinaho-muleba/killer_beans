package com.killerbean.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		System.out.println(
				"-----------------------------------------" +
				"\nWelcome to Killer Beans  \n" +
				"-----------------------------------------" +
				"\nType help to see available commands." +
				"\nNote: you must be signed in.\n" +
				"-----------------------------------------\n"
		);

		SpringApplication.run(Application.class, args);
	}

}
