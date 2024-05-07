package negative;

import client.EVPrimeClient;
import data.SignUpLoginDataFactory;
import io.restassured.response.Response;
import models.requests.SignUpLoginRequest;
import models.response.SignUpUnsuccessfulResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static builder.SignUpBuilder.createBodyForSignUp;
import static org.junit.Assert.assertEquals;

public class SignUpNegativeTests {

    @Test
    public void wrongEmailFormatSignUp() {

        SignUpLoginRequest signUpRequest = new SignUpLoginDataFactory(createBodyForSignUp())
                .setEmail(RandomStringUtils.randomAlphanumeric(10) + "_mail.com")
                .setPassword(RandomStringUtils.randomAlphanumeric(10))
                .createRequest();

        Response response = new EVPrimeClient()
                .signUp(signUpRequest);

        SignUpUnsuccessfulResponse signUpResponse = response.body().as(SignUpUnsuccessfulResponse.class);

        assertEquals(422, response.statusCode());
        assertEquals("User signup failed due to validation errors.", signUpResponse.getMessage());
        assertEquals("Invalid email.", signUpResponse.getErrors().getEmail());

    }

    @Test
    public void emailAlreadyExistsSignUp() {
        SignUpLoginRequest signUpRequest = new SignUpLoginDataFactory(createBodyForSignUp())
                .setEmail(RandomStringUtils.randomAlphanumeric(10) + "@mail.com")
                .setPassword(RandomStringUtils.randomAlphanumeric(10))
                .createRequest();

        new EVPrimeClient()
                .signUp(signUpRequest);

        Response secondresponse = new EVPrimeClient()
                .signUp(signUpRequest);

        SignUpUnsuccessfulResponse signUpResponse = secondresponse.body().as(SignUpUnsuccessfulResponse.class);

        assertEquals(422, secondresponse.statusCode());
        assertEquals("User signup failed due to validation errors.", signUpResponse.getMessage());
        assertEquals("Email exists already.", signUpResponse.getErrors().getEmail());

    }

    @Test
    public void invalidPasswordSignUp() {

        SignUpLoginRequest signUpRequest = new SignUpLoginDataFactory(createBodyForSignUp())
                .setEmail(RandomStringUtils.randomAlphanumeric(10) + "@mail.com")
                .setPassword(RandomStringUtils.randomAlphanumeric(5))
                .createRequest();

        Response response = new EVPrimeClient()
                .signUp(signUpRequest);

        SignUpUnsuccessfulResponse signUpResponse = response.body().as(SignUpUnsuccessfulResponse.class);

        assertEquals(422, response.statusCode());
        assertEquals("User signup failed due to validation errors.", signUpResponse.getMessage());
        assertEquals("Invalid password. Must be at least 6 characters long.", signUpResponse.getErrors().getPassword());

    }
}
