package com.foodlist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoodlistApplicationTests {

	@Test
	void contextLoads() {
		String version = org.springframework.core.SpringVersion.getVersion();
		String securityVs = org.springframework.security.core.SpringSecurityCoreVersion.getVersion();
		System.out.println("version : " + version);
		System.out.println("security verison : " + securityVs);
	}

}
