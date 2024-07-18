package com.cts.adb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.adb.entities.Contact;
import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.service.ContactService;

import jakarta.validation.Valid;

@RestController // @Controller + @ResponseBody
@RequestMapping("/")
public class ContactsApi {

	@Autowired
	private ContactService contactService;

	@GetMapping
	public ResponseEntity<List<Contact>> retriveAllContactsAction() throws AddressBookException {
		return ResponseEntity.ok(contactService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Contact> retriveContactByIdAction(@PathVariable("id") int contactId)
			throws AddressBookException {
		Contact contact = contactService.getById(contactId);
		return contact != null ? ResponseEntity.ok(contact) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteContactByIdAction(@PathVariable("id") int contactId)
			throws AddressBookException {
		contactService.deleteById(contactId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	@PostMapping
	public ResponseEntity<Contact> addContactAction(@RequestBody @Valid Contact contact,BindingResult result) throws AddressBookException{
		if(result.hasErrors()) {
			throw new AddressBookException(
					result.getAllErrors().stream().map(err -> err.getDefaultMessage()).reduce("", (e1,e2) -> e1+","+e2)
			);
		}
		
		return new ResponseEntity<>(contactService.add(contact),HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Contact> updateContactAction(@RequestBody @Valid Contact contact,BindingResult result) throws AddressBookException{
		if(result.hasErrors()) {
			throw new AddressBookException(
					result.getAllErrors().stream().map(err -> err.getDefaultMessage()).reduce("", (e1,e2) -> e1+","+e2)
			);
		}
		
		return new ResponseEntity<>(contactService.update(contact),HttpStatus.ACCEPTED);
	}
}
