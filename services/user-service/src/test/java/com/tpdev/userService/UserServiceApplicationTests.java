package com.tpdev.userService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
        properties = {
                "spring.kafka.admin.auto-create=false",
                "spring.kafka.admin.fail-fast=false",
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration"
        }
)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceApplicationTests {

    @MockBean
    private StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {
    }
}
