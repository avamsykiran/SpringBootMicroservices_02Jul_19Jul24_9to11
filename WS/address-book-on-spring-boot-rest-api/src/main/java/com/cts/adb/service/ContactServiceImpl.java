package com.cts.adb.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.adb.entities.Contact;
import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.repos.ContactRepo;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepo contactRepo;

	private double computeAge(LocalDate dob) {
		return Period.between(dob, LocalDate.now()).toTotalMonths() / 12.0;
	}

	@Override
	public List<Contact> getAll() throws AddressBookException {
		return contactRepo.findAll();
	}

	@Override
	public Contact getById(int contactId) throws AddressBookException {
		return contactRepo.findById(contactId).orElse(null);
	}

	@Override
	public Contact add(Contact contact) throws AddressBookException {
		contact.setAge(computeAge(contact.getDateOfBirth()));
		return contactRepo.save(contact);
	}

	@Override
	public Contact update(Contact contact) throws AddressBookException {
		if(!contactRepo.existsById(contact.getContactId())) {
			throw new AddressBookException("Contact Not Found");
		}
		
		contact.setAge(computeAge(contact.getDateOfBirth()));
		return contactRepo.save(contact);
	}

	@Override
	public void deleteById(int contactId) throws AddressBookException {
		contactRepo.deleteById(contactId);
	}

}
