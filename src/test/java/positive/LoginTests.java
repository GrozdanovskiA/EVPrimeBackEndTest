package positive;

import client.EVPrimeClient;
import data.SignUpLoginDataFactory;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import models.requests.SignUpLoginRequest;
import models.response.LoginResponse;
import util.DateBuilder;


import static builder.SignUpBuilder.createBodyForSignUp;
import static org.junit.Assert.*;

public class LoginTests {

    SignUpLoginRequest signUpLoginRequest;
    DateBuilder dateBuilder = new DateBuilder();
    @Before
    public void userSetUp(){
         signUpLoginRequest = new SignUpLoginDataFactory(createBodyForSignUp())
                .setEmail(RandomStringUtils.randomAlphanumeric(10) + "@mail.com")
                .setPassword(RandomStringUtils.randomAlphanumeric(10))
                .createRequest();

                 new EVPrimeClient()
                .signUp(signUpLoginRequest);


    }

    @Test
    public void successfulLoginTest(){

        Response response = new EVPrimeClient()
                .login(signUpLoginRequest);

        LoginResponse loginResponse = response.body().as(LoginResponse.class);

        assertEquals(200, response.statusCode());
        assertNotNull(loginResponse.getToken());
        assertTrue(loginResponse.getExpirationTime().contains(dateBuilder.currentDateAndTime()));

    }


}
