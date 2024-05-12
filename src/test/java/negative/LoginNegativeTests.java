package negative;

import client.EVPrimeClient;
import data.SignUpLoginDataFactory;
import io.restassured.response.Response;
import models.requests.SignUpLoginRequest;
import models.response.LoginResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static builder.SignUpBuilder.createBodyForSignUp;
import static org.junit.Assert.assertEquals;

public class LoginNegativeTests {

    SignUpLoginRequest signUpLoginRequest;

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
    public void wrongEmailLoginTest(){
        signUpLoginRequest = new SignUpLoginDataFactory(createBodyForSignUp())
                .setEmail("asdasd@adajf.com")
                .setPassword("asdasdas")
                .createRequest();

        Response response = new EVPrimeClient()
                .login(signUpLoginRequest);

        LoginResponse loginResponse = response.body().as(LoginResponse.class);

        assertEquals(401, response.statusCode());
        assertEquals("Authentication failed.",loginResponse.getMessage());

    }

    @Test
    public void wrongPasswordLoginTest(){
        signUpLoginRequest = new SignUpLoginDataFactory(createBodyForSignUp())
                .setEmail("aleksandar@hotmail.com")
                .setPassword("password1111")
                .createRequest();

        Response response = new EVPrimeClient()
                .login(signUpLoginRequest);

        LoginResponse loginResponse = response.body().as(LoginResponse.class);

        assertEquals(422, response.statusCode());
        assertEquals("Invalid credentials.",loginResponse.getMessage());
        assertEquals("Invalid email or password entered.", loginResponse.getErrors().getCredentials());

    }
}
