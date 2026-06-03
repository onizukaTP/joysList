package com.tpdev.joysList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		properties = {
				"spring.kafka.admin.auto-create=false",
				"spring.kafka.admin.fail-fast=false"
		}
)
class JoysListApplicationTests {

	@Test
	void contextLoads() {
	}

}
