package com.learn.simplegateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

import authentication.filter.RateLimitByIpGatewayFilter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleGatewayApplicationTests {

   @Test
   public void test(){
   }

}
