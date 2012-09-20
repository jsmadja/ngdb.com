package com.ngdb.entities.user;

import com.ngdb.entities.AbstractEntity;
import org.apache.commons.lang.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Entity
public class Token extends AbstractEntity {

	@ManyToOne
	private User user;

	private String value;

	Token() {
	}

	public Token(User user) {
		this.user = user;
		this.value = generateToken(user.getEmail());
	}

	private String generateToken(String email) {
		String salt = RandomStringUtils.random(5);
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			return byteArray2Hex(md.digest((salt + email).getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private String byteArray2Hex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	public User getUser() {
		return user;
	}

	public String getValue() {
		return value;
	}

	public boolean matches(String token) {
		return value.equals(token);
	}

}
