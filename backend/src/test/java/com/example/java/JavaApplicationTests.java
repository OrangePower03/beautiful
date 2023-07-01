package com.example.java;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class JavaApplicationTests {

	@Test
	void test() {
		List<Integer> list=new ArrayList<>(5);
		list.add(0);
		list.add(1);
		list.add(2);


	}

}
