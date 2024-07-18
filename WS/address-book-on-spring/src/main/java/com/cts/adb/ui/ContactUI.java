package com.cts.adb.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.models.Contact;
import com.cts.adb.service.ContactService;

@Component
public class ContactUI {

	@Autowired
	private ContactService contactService;

	@Autowired
	private Scanner scan;

	public void run() {
		System.out.println("Address Book 2.0");
		System.out.println("-------------------------------------------------------------");

		String cmd = null;

		while (!"quit".equalsIgnoreCase(cmd)) {

			System.out.println("Command [add/delete/list/quit]? ");
			cmd = scan.nextLine().toLowerCase();

			switch (cmd) {
			case "add":
				doAdd();
				break;
			case "delete":
				doDelete();
				break;
			case "list":
				doList();
				break;
			case "quit":
				System.out.println("Thank you!");
				break;
			default:
				System.out.println("UNKNOWN COMMAND");
			}
		}

		scan.close();
		System.out.println("Application Terminated!");
	}

	private void doAdd() {
		Contact contact = new Contact();

		System.out.println("Full Name? ");
		contact.setFullName(scan.nextLine());
		System.out.println("Mobile? ");
		contact.setMobile(scan.nextLine());
		System.out.println("MailId? ");
		contact.setMailId(scan.nextLine());
		System.out.println("Date Of Birth (dd-MM-yyyy)? ");
		contact.setDateOfBirth(LocalDate.parse(scan.nextLine(), ContactService.DT_FORMATTER));

		try {
			contactService.add(contact);
			System.out.println("Contact Saved!");
		} catch (AddressBookException e) {
			System.out.println(e.getMessage());
		}
	}

	private void doDelete() {
		System.out.println("Contact Id? ");
		int contactId = scan.nextInt();
		try {
			contactService.deleteById(contactId);
			System.out.println("Contact Removed! ");
		} catch (AddressBookException e) {
			System.out.println(e.getMessage());
		}
	}

	private void doList() {

		try {
			List<Contact> contacts = contactService.getAll();

			if (contacts == null || contacts.isEmpty()) {
				System.out.println("No Contacts in the store.");
			} else {
				contacts.stream().forEach(System.out::println);
			}
		} catch (AddressBookException e) {
			System.out.println(e.getMessage());
		}
	}
}
