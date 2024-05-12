package positive;

import client.EVPrimeClient;
import data.SignUpLoginDataFactory;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import models.requests.SignUpLoginRequest;
import models.response.SignUpSuccessfulResponse;
import static builder.SignUpBuilder.createBodyForSignUp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SignUpTests {

   @Test
   public void succesfulSignUpTest(){

       SignUpLoginRequest signUpRequest = new SignUpLoginDataFactory(createBodyForSignUp())
               .setEmail(RandomStringUtils.randomAlphanumeric(10) + "@mail.com")
               .setPassword(RandomStringUtils.randomAlphanumeric(10))
               .createRequest();

       Response response = new EVPrimeClient()
               .signUp(signUpRequest);

       SignUpSuccessfulResponse signUpResponse = response.body().as(SignUpSuccessfulResponse.class);

       assertEquals(201, response.statusCode());
       assertEquals("User created.", signUpResponse.getMessage());
       assertNotNull(signUpResponse.getUser().getId());
       assertEquals(signUpRequest.getEmail(), signUpResponse.getUser().getEmail());
       assertNotNull(signUpResponse.getToken());

   }

}

