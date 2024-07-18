package com.cts.adb;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.ui.ContactUI;

@Configuration
@ComponentScan("com.cts.adb")
@PropertySource("classpath:application.properties")
public class AddressBookApplication {

	public static void main(String[] args) throws AddressBookException {

		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(AddressBookApplication.class);
		
		ContactUI contactUI = (ContactUI) context.getBean("contactUI");
		contactUI.run();
	}

	@Bean
	public Scanner scan() {
		return new Scanner(System.in);
	}
}
