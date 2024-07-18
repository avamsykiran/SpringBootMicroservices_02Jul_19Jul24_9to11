package com.cts.adb.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.cts.adb.dao.ContactDAO;
import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.models.Contact;

public class ContactServiceImpl implements ContactService {

	private ContactDAO contactDAO;
	
	public ContactServiceImpl(ContactDAO contactDAO) {
		this.contactDAO=contactDAO;
	}
	
	private boolean isValidFullName(String fullName) {
		return fullName!=null && !fullName.isEmpty() && fullName.length()>=5;
	}
	
	private boolean isValidDateOfBirth(LocalDate dateOfBirth) {
		return dateOfBirth!=null && dateOfBirth.isBefore(LocalDate.now());
	}
	
	private boolean isValidMobileNumber(String mobileNumber) {
		return mobileNumber!=null && !mobileNumber.isEmpty() && mobileNumber.matches("[1-9][0-9]{9}");
	}
	
	private boolean isValidMailId(String mailId) {
		return mailId!=null && !mailId.isEmpty() && mailId.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
	}
	
	private boolean isValidContact(Contact contact) throws AddressBookException {

		List<String> errMsgs = new ArrayList<>();
		
		if(contact!=null) {
			if(!isValidFullName(contact.getFullName())) {
				errMsgs.add("Full Name is a mandate field and must be of atleast 5 letters in length");
			}
			if(!isValidDateOfBirth(contact.getDateOfBirth())) {
				errMsgs.add("Date Of Birth is a mandate field and must be past one");
			}
			if(!isValidMobileNumber(contact.getMobile())) {
				errMsgs.add("Mobiel Number is a mandate field and must be a 10 digit number");
			}
			if(!isValidMailId(contact.getMailId())) {
				errMsgs.add("Mail Id is a mandate field and must be a valid email");
			}
		}else {
			errMsgs.add("No Contact Details Received");
		}
		
		if(!errMsgs.isEmpty()) {
			throw new AddressBookException(errMsgs.stream().reduce("Validation Errors", (m1,m2) -> String.format("%s \n %s", m1,m2)));
		}
		
		return true;
	}
	
	private double computeAge(LocalDate dob) {
		return Period.between(dob, LocalDate.now()).toTotalMonths()/12.0;
	}
	
	@Override
	public List<Contact> getAll() throws AddressBookException {
		return contactDAO.getAll();
	}

	@Override
	public Contact getById(int contactId) throws AddressBookException {
		return contactDAO.getById(contactId);
	}

	@Override
	public Contact add(Contact contact) throws AddressBookException {
		if(isValidContact(contact)) {
			contact.setAge(computeAge(contact.getDateOfBirth()));
			contact = contactDAO.add(contact);
		}
		return contact;
	}

	@Override
	public Contact update(Contact contact) throws AddressBookException {
		if(isValidContact(contact)) {
			contact.setAge(computeAge(contact.getDateOfBirth()));
			contact = contactDAO.update(contact);
		}
		return contact;
	}

	@Override
	public void deleteById(int contactId) throws AddressBookException {
		contactDAO.deleteById(contactId);
	}

}
