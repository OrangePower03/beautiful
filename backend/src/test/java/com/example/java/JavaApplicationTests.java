package com.example.java;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class JavaApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void test() {
//		System.out.println(
//				passwordEncoder.encode("123123"));



	}

}
