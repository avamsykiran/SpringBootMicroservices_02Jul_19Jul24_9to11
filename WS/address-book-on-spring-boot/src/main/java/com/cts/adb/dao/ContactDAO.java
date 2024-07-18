package com.cts.adb.dao;

import java.util.List;

import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.models.Contact;

public interface ContactDAO {
	List<Contact> getAll() throws AddressBookException;
	Contact getById(int contactId) throws AddressBookException;
	Contact add(Contact contact) throws AddressBookException;
	Contact update(Contact contact) throws AddressBookException;
	void deleteById(int contactId) throws AddressBookException;
}
