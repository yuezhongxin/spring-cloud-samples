package com.xishuai.demo.authorizationserveroauth2;

import com.xishuai.demo.authorizationserveroauth2.util.HttpHeaderUtils;
import com.xishuai.demo.authorizationserveroauth2.util.SmsKeyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationServerOauth2ApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void contextLoads() {
		String key = SmsKeyUtils.getSmsCodeKey(HttpHeaderUtils.DEFAULT_DEVICE,"13651805404");
		String value = "hello world";
		stringRedisTemplate.opsForValue().set(key, value, 1, TimeUnit.DAYS);
		Assert.assertEquals(value, stringRedisTemplate.opsForValue().get(key));

	}
}
