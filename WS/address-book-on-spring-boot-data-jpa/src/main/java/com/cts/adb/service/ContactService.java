package com.cts.adb.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.cts.adb.entities.Contact;
import com.cts.adb.exceptions.AddressBookException;

public interface ContactService {

	public static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	List<Contact> getAll() throws AddressBookException;
	Contact getById(int contactId) throws AddressBookException;
	Contact add(Contact contact) throws AddressBookException;
	Contact update(Contact contact) throws AddressBookException;
	void deleteById(int contactId) throws AddressBookException;
}
