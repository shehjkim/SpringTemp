package com.company.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
	public static void main(String[] args) {
		BCryptPasswordEncoder bcypt = new BCryptPasswordEncoder();
		String pw = bcypt.encode("1234");
				System.out.println(pw);
		bcypt.matches("1111", pw);
	}

}
