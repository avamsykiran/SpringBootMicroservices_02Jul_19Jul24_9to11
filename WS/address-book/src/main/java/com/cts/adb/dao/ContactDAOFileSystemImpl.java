package com.cts.adb.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.models.Contact;

public class ContactDAOFileSystemImpl implements ContactDAO {

	public static final String DATA_FILE_NAME = "addressBook.csv";

	private List<Contact> contacts;
	private int nextContactId;

	public ContactDAOFileSystemImpl() throws AddressBookException {
		readData();
	}

	private void readData() throws AddressBookException {
		Path filePath = Paths.get(DATA_FILE_NAME);

		if (Files.notExists(filePath)) {
			this.contacts = new ArrayList<>();
			this.nextContactId = 1;
		} else {
			try {
				List<String> lines = Files.readAllLines(filePath);
				if (lines != null && !lines.isEmpty()) {
					this.contacts = lines.stream().map(line -> {
						String fields[] = line.split(",");
						Contact c = new Contact();
						c.setContactId(Integer.parseInt(fields[0]));
						c.setFullName(fields[1]);
						c.setMobile(fields[2]);
						c.setMailId(fields[3]);
						c.setDateOfBirth(LocalDate.parse(fields[4]));
						return c;
					}).collect(Collectors.toList());
					this.nextContactId = this.contacts.get(contacts.size() - 1).getContactId() + 1;
				}
			} catch (IOException e) {
				// log the IOException
				throw new AddressBookException("Unable to retrive data from the store!");
			}

		}
	}

	private void writeData() throws AddressBookException {
		Path filePath = Paths.get(DATA_FILE_NAME);
		List<String> lines = contacts.stream().map(c -> String.format("%d,%s,%s,%s,%s", c.getContactId(),
				c.getFullName(), c.getMobile(), c.getMailId(), c.getDateOfBirth().toString()))
				.collect(Collectors.toList());

		try {
			Files.write(filePath, lines);
		} catch (IOException e) {
			// log the IOException
			throw new AddressBookException("Unable to retrive data from the store!");
		}
	}

	@Override
	public List<Contact> getAll() throws AddressBookException {
		return new ArrayList<>(this.contacts);
	}

	@Override
	public Contact getById(int contactId) throws AddressBookException {
		return contacts.stream().filter(c -> c.getContactId() == contactId).findFirst().orElse(null);
	}

	@Override
	public Contact add(Contact contact) throws AddressBookException {
		contact.setContactId(nextContactId++);
		contacts.add(contact);
		writeData();
		return contact;
	}

	@Override
	public Contact update(Contact contact) throws AddressBookException {
		int index = contacts.indexOf(contact);
		if (index > -1) {
			contacts.set(index, contact);
			writeData();
		}
		return contact;
	}

	@Override
	public void deleteById(int contactId) throws AddressBookException {
		contacts.remove(contacts.stream().filter(c -> c.getContactId() == contactId).findFirst().orElse(null));
		writeData();
	}

}