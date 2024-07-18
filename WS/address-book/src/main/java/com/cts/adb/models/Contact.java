package com.cts.adb.models;

import java.time.LocalDate;
import java.util.Objects;

public class Contact {

	private int contactId;
	private String fullName;
	private String mobile;
	private String mailId;
	private LocalDate dateOfBirth;
	private double age;
	
	public Contact() {
		// TODO Auto-generated constructor stub
	}

	public Contact(int contactId, String fullName, String mobile, String mailId, LocalDate dateOfBirth) {
		super();
		this.contactId = contactId;
		this.fullName = fullName;
		this.mobile = mobile;
		this.mailId = mailId;
		this.dateOfBirth = dateOfBirth;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contactId, dateOfBirth, fullName, mailId, mobile);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		return contactId == other.contactId && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(fullName, other.fullName) && Objects.equals(mailId, other.mailId)
				&& Objects.equals(mobile, other.mobile);
	}

	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", fullName=" + fullName + ", mobile=" + mobile + ", mailId="
				+ mailId + ", dateOfBirth=" + dateOfBirth + ", age=" + age + "]";
	}
	
}
