package com.cts.adb;

import com.cts.adb.dao.ContactDAO;
import com.cts.adb.dao.ContactDAOFileSystemImpl;
import com.cts.adb.dao.ContactDAOJDBCImpl;
import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.service.ContactService;
import com.cts.adb.service.ContactServiceImpl;
import com.cts.adb.ui.ContactUI;

public class AddressBookApplication {

	public static void main(String[] args) throws AddressBookException {

		ContactDAO contactDao = new ContactDAOJDBCImpl(); //new ContactDAOFileSystemImpl(); //new ContactDAOInMemImpl();		
		ContactService contactService = new ContactServiceImpl(contactDao); //dependency injection through constructor
		ContactUI contactUI = new ContactUI(contactService); //dependency injection through constructor
		contactUI.run();

	}

}
