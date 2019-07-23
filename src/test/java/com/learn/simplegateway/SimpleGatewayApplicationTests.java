package com.learn.simplegateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import utils.JwtUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleGatewayApplicationTests {

    @Test
    public void test() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjM5MDE0NjQsInVzZXJfbmFtZSI6IjE4ODgiLCJhdXRob3JpdGllcyI6WyJST0xFX1dCVVNFUiJdLCJqdGkiOiI5ZGI2MTEwNi05YTViLTRjOTYtOWNhMS0yZGE2ODBhNWEwM2YiLCJjbGllbnRfaWQiOiJhbmRyb2lkIiwic2NvcGUiOlsieHgiXX0.H1jJpGt7EH6apcWF08HoxV0Z7BwLfAy7zNaW-tof5xaq5zqPxVbrq2H4mwcK4g3BpGbEQkgdT384M2z33cFqMsyRAAl7a8HQuuDDCD7RcXprSLPZI3lMA29Gzbj5f73JETWYtvQAkiq8Bx4GKZFRdDB-hHk1imHIry2Sd0qPt11eeY7OQbZrjuqjUVtFozgHUJMUX0nayP_6YAOL6eA1wqJbfkwKP1Cy9YtMjHYbHITpiXv8H1ILGtq5E7u07v5GW7zA8LPlmUOmOr_YTR6emVqD1hbD35oR6xe8hs_FQK856ZqCyQWIeZPc6Lb6_XkIffkj1KH3s1apn8rtl7l9yg";
        try {
            ZonedDateTime expired = JwtUtils.getExpired(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
