package com.tpdev.joysList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.tpdev.events.UserRegisteredEvent;

@SpringBootTest(
		properties = {
				"spring.kafka.admin.auto-create=false",
				"spring.kafka.admin.fail-fast=false"
		}
)
@ActiveProfiles("test")
class ListingServiceApplicationTests {

	@MockitoBean
	private KafkaTemplate<String, Object> kafkaTemplate;

	@MockitoBean
	private KafkaTemplate<String, UserRegisteredEvent> userKafkaTemplate;

	@MockitoBean
	private RedisConnectionFactory redisConnectionFactory;

	@MockitoBean
	private RedisCacheManager redisCacheManager;

	@Test
	void contextLoads() {
	}

}
