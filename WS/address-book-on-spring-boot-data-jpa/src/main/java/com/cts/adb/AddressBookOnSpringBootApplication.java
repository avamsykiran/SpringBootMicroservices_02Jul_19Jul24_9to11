package com.cts.adb;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //@Configuration + @ComponentScan("com.cts.adb") + @PropertySource("classpath:application.proper
public class AddressBookOnSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressBookOnSpringBootApplication.class, args);
		/*
		 * This run method does the below...!
		 * 1. ApplciationContext is created
		 * 2. Scan all components and will register the components and its beans on the context
		 * 3. Invoke the Runners (classes that implement CommandLineRunner) (if any)
		 * 4. Start the embeded server (if any)
		 * 5. The application is controlled by the server if any, and after the server shuts down
		 * 6. The context is destroyed and the application is terminated.
		 */
	}

	@Bean
	public Scanner scan() {
		return new Scanner(System.in);
	}
}
