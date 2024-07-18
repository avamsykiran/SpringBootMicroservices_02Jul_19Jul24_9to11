package com.cts.adb.entities;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="contacts")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cid")
	private Integer contactId;
	
	@Column(name="fnm")
	@NotNull(message = "Full Name is a mandatory field")
	@NotBlank(message = "Full Name is a mandatory field")
	@Size(min = 5,max = 30,message = "Full Name must be atleast 5 and atmax 30 letters in length")
	private String fullName;
	
	@Column(name="mob")
	@NotNull(message = "Mobile is a mandatory field")
	@NotBlank(message = "Mobile is a mandatory field")
	@Pattern(regexp = "[1-9][0-9]{9}",message = "Mobiel is expected to be a 10 digit number")
	private String mobile;
	
	@Column(name="mid")
	@NotNull(message = "Mail Id is a mandatory field")
	@NotBlank(message = "Mail Id is a mandatory field")
	@Email(message = "A valid mail id expected")
	private String mailId;
	
	@Column(name="dob")
	@NotNull(message = "Date Of Birth is a mandatory field")
	@PastOrPresent(message = "Date of Birth can not be a future date")
	private LocalDate dateOfBirth;
	
	@Column(name="age")
	private Double age;
	
	public Contact() {
		// TODO Auto-generated constructor stub
	}

	public Contact(Integer contactId, String fullName, String mobile, String mailId, LocalDate dateOfBirth) {
		super();
		this.contactId = contactId;
		this.fullName = fullName;
		this.mobile = mobile;
		this.mailId = mailId;
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
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

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", fullName=" + fullName + ", mobile=" + mobile + ", mailId="
				+ mailId + ", dateOfBirth=" + dateOfBirth + ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, contactId, dateOfBirth, fullName, mailId, mobile);
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
		return Objects.equals(age, other.age) && Objects.equals(contactId, other.contactId)
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(mailId, other.mailId) && Objects.equals(mobile, other.mobile);
	}

	
}
