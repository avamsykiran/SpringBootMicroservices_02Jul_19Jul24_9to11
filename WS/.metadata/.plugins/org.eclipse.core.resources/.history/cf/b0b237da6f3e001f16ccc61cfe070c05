package com.cts.adb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.cts.adb.exceptions.AddressBookException;
import com.cts.adb.models.Contact;

@Repository
public class ContactDAOJDBCImpl implements ContactDAO {

	@Value("${con.str}")
	private String CON_STR;
	
	@Value("${db.user}")
	private String DB_USER;
	
	@Value("${db.pwd}")
	private String DB_PWD;

	public static final String GET_ALL_QRY = "SELECT cid,fnm,mob,mid,dob,age FROM contacts";
	public static final String GET_BY_ID_QRY = "SELECT cid,fnm,mob,mid,dob,age FROM contacts WHERE cid=?";
	public static final String INS_QRY = "INSERT INTO contacts(fnm,mob,mid,dob,age) VALUES(?,?,?,?,?)";
	public static final String UPD_QRY = "UPDATE contacts SET fnm=?,mob=?,mid=?,dob=?,age=? WHERE cid=?";
	public static final String DEL_QRY = "DELETE FROM contacts WHERE cid=?";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CON_STR, DB_USER, DB_PWD);
	}

	@Override
	public List<Contact> getAll() throws AddressBookException {
		List<Contact> contacts = new ArrayList<>();

		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(GET_ALL_QRY)) {
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Contact c = new Contact();
				c.setContactId(rs.getInt(1));
				c.setFullName(rs.getString(2));
				c.setMobile(rs.getString(3));
				c.setMailId(rs.getString(4));
				c.setDateOfBirth(rs.getDate(5).toLocalDate());
				c.setAge(rs.getDouble(6));
				contacts.add(c);
			}
		} catch (SQLException exp) {
			// log the exp
			throw new AddressBookException("Sorry soemthing went wrong with the database");
		}

		return contacts;
	}

	@Override
	public Contact getById(int contactId) throws AddressBookException {
		Contact c = null;
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(GET_BY_ID_QRY)) {
			pst.setInt(1, contactId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				c = new Contact();
				c.setContactId(rs.getInt(1));
				c.setFullName(rs.getString(2));
				c.setMobile(rs.getString(3));
				c.setMailId(rs.getString(4));
				c.setDateOfBirth(rs.getDate(5).toLocalDate());
				c.setAge(rs.getDouble(6));
			}
		} catch (SQLException exp) {
			// log the exp
			throw new AddressBookException("Sorry soemthing went wrong with the database");
		}
		return c;
	}

	@Override
	public Contact add(Contact contact) throws AddressBookException {
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(INS_QRY)) {
			if (contact != null) {

				pst.setString(1, contact.getFullName());
				pst.setString(2, contact.getMobile());
				pst.setString(3, contact.getMailId());
				pst.setDate(4, java.sql.Date.valueOf(contact.getDateOfBirth()));
				pst.setDouble(5, contact.getAge());

				pst.executeUpdate();
			}
		} catch (SQLException exp) {
			// log the exp
			throw new AddressBookException("Sorry soemthing went wrong with the database");
		}
		return contact;
	}

	@Override
	public Contact update(Contact contact) throws AddressBookException {
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(UPD_QRY)) {
			if (contact != null) {

				pst.setString(1, contact.getFullName());
				pst.setString(2, contact.getMobile());
				pst.setString(3, contact.getMailId());
				pst.setDate(4, java.sql.Date.valueOf(contact.getDateOfBirth()));
				pst.setDouble(5, contact.getAge());
				pst.setInt(6, contact.getContactId());
				
				pst.executeUpdate();
			}
		} catch (SQLException exp) {
			// log the exp
			throw new AddressBookException("Sorry soemthing went wrong with the database");
		}
		return contact;
	}

	@Override
	public void deleteById(int contactId) throws AddressBookException {
		try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(DEL_QRY)) {
			pst.setInt(1, contactId);
			pst.executeUpdate();
		} catch (SQLException exp) {
			// log the exp
			throw new AddressBookException("Sorry soemthing went wrong with the database");
		}
	}

}
