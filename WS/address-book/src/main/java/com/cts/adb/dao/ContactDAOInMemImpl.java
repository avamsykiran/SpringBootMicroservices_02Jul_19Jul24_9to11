package com.cts.adb.dao;

import java.util.ArrayList;
import java.util.List;

import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.models.Contact;

public class ContactDAOInMemImpl implements ContactDAO {

	private List<Contact> contacts;
	private int nextContactId;

	public ContactDAOInMemImpl() {
		this.contacts = new ArrayList<>();
		this.nextContactId = 1;
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
		return contact;
	}

	@Override
	public Contact update(Contact contact) throws AddressBookException {
		int index = contacts.indexOf(contact);
		if (index > -1) {
			contacts.set(index, contact);
		}
		return contact;
	}

	@Override
	public void deleteById(int contactId) throws AddressBookException {
		contacts.remove(contacts.stream().filter(c -> c.getContactId() == contactId).findFirst().orElse(null));
	}

}
