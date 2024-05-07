package negative;

import client.EVPrimeClient;
import data.PostEventDataFactory;
import data.SignUpLoginDataFactory;
import io.restassured.response.Response;
import models.requests.PostUpdateEventRequest;
import models.requests.SignUpLoginRequest;
import models.response.LoginResponse;
import models.response.PostUpdateDeleteEventResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import util.DateBuilder;
import static builder.PostEventObjectBuilder.createBodyForPostEvent;
import static builder.SignUpBuilder.createBodyForSignUp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PostEventNegativeTests {

    private SignUpLoginRequest signUpRequest;
    private LoginResponse loginResponseBody;
    private PostUpdateEventRequest requestBody;
    DateBuilder dateBuilder= new DateBuilder();

    @Before
    public void setUp() {
        signUpRequest = new SignUpLoginDataFactory(createBodyForSignUp())
                .setEmail(RandomStringUtils.randomAlphanumeric(10) + dateBuilder.currentDateAndTime() + "@mail.com")
                .setPassword(RandomStringUtils.randomAlphanumeric(10))
                .createRequest();

        new EVPrimeClient()
                .signUp(signUpRequest);

        Response loginResponse = new EVPrimeClient()
                .login(signUpRequest);

        loginResponseBody = loginResponse.body().as(LoginResponse.class);


    }

    @Test
    public void unsuccessfulRequestNoAuthorizationToken(){

        requestBody = new PostEventDataFactory(createBodyForPostEvent())
                .setTitle(RandomStringUtils.randomAlphanumeric(8))
                .setImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.goal.com%2Fen-sg%2Fnews%2Fliverpool-vs-manchester-united-lineups-live-updates%2Fbltf4a9e3c54804c6b8&psig=AOvVaw11pYwQiECKpPWu17jL6s6X&ust=1712771074871000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOiy883XtYUDFQAAAAAdAAAAABAE")
                .setDate(dateBuilder.currentDate())
                .setLocation(RandomStringUtils.randomAlphabetic(6))
                .setDescription(RandomStringUtils.randomAlphabetic(6))
                .createRequest();

        Response response = new EVPrimeClient()
                .postEvent(requestBody,"");

        PostUpdateDeleteEventResponse postResponse = response.body().as(PostUpdateDeleteEventResponse.class);

        assertEquals(401, response.statusCode());
        assertTrue(postResponse.getMessage().contains("Not authenticated."));

    }
    @Test
    public void unsuccessfulRequestEmptyTitle(){

        requestBody = new PostEventDataFactory(createBodyForPostEvent())
                .setTitle("")
                .setImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.goal.com%2Fen-sg%2Fnews%2Fliverpool-vs-manchester-united-lineups-live-updates%2Fbltf4a9e3c54804c6b8&psig=AOvVaw11pYwQiECKpPWu17jL6s6X&ust=1712771074871000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOiy883XtYUDFQAAAAAdAAAAABAE")
                .setDate(dateBuilder.currentDate())
                .setLocation(RandomStringUtils.randomAlphabetic(6))
                .setDescription(RandomStringUtils.randomAlphabetic(6))
                .createRequest();

        Response response = new EVPrimeClient()
                .postEvent(requestBody, loginResponseBody.getToken());

        PostUpdateDeleteEventResponse postResponse = response.body().as(PostUpdateDeleteEventResponse.class);


        assertEquals(422, response.statusCode());
        assertTrue(postResponse.getMessage().contains("Adding the event failed due to validation errors."));
        assertTrue(postResponse.getErrors().getTitle().contains("Invalid title."));

    }
    @Test
    public void unsuccessfulRequestEmptyImage (){
        requestBody = new PostEventDataFactory(createBodyForPostEvent())
                .setTitle(RandomStringUtils.randomAlphanumeric(8))
                .setImage("")
                .setDate(dateBuilder.currentDate())
                .setLocation(RandomStringUtils.randomAlphabetic(6))
                .setDescription(RandomStringUtils.randomAlphabetic(6))
                .createRequest();

        Response response = new EVPrimeClient()
                .postEvent(requestBody, loginResponseBody.getToken());

        PostUpdateDeleteEventResponse postResponse = response.body().as(PostUpdateDeleteEventResponse.class);

        assertEquals(422, response.statusCode());
        assertTrue(postResponse.getMessage().contains("Adding the event failed due to validation errors."));
        assertTrue(postResponse.getErrors().getImage().contains("Invalid image."));
    }
    @Test
    public void  unsuccessfulRequestEmptyDate(){
        requestBody = new PostEventDataFactory(createBodyForPostEvent())
                .setTitle(RandomStringUtils.randomAlphanumeric(8))
                .setImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.goal.com%2Fen-sg%2Fnews%2Fliverpool-vs-manchester-united-lineups-live-updates%2Fbltf4a9e3c54804c6b8&psig=AOvVaw11pYwQiECKpPWu17jL6s6X&ust=1712771074871000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOiy883XtYUDFQAAAAAdAAAAABAE")
                .setDate("")
                .setLocation(RandomStringUtils.randomAlphabetic(6))
                .setDescription(RandomStringUtils.randomAlphabetic(6))
                .createRequest();

        Response response = new EVPrimeClient()
                .postEvent(requestBody, loginResponseBody.getToken());

        PostUpdateDeleteEventResponse postResponse = response.body().as(PostUpdateDeleteEventResponse.class);

        assertEquals(422, response.statusCode());
        assertTrue(postResponse.getMessage().contains("Adding the event failed due to validation errors."));
        assertTrue(postResponse.getErrors().getDate().contains("Invalid date."));

    }
    @Test
    public void  unsuccessfulRequestEmptyLocation(){
        requestBody = new PostEventDataFactory(createBodyForPostEvent())
                .setTitle(RandomStringUtils.randomAlphanumeric(8))
                .setImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.goal.com%2Fen-sg%2Fnews%2Fliverpool-vs-manchester-united-lineups-live-updates%2Fbltf4a9e3c54804c6b8&psig=AOvVaw11pYwQiECKpPWu17jL6s6X&ust=1712771074871000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOiy883XtYUDFQAAAAAdAAAAABAE")
                .setDate(dateBuilder.currentDate())
                .setLocation("")
                .setDescription(RandomStringUtils.randomAlphabetic(6))
                .createRequest();

        Response response = new EVPrimeClient()
                .postEvent(requestBody, loginResponseBody.getToken());

        PostUpdateDeleteEventResponse postResponse = response.body().as(PostUpdateDeleteEventResponse.class);

        assertEquals(422, response.statusCode());
        assertTrue(postResponse.getMessage().contains("Adding the event failed due to validation errors."));
        assertTrue(postResponse.getErrors().getDescription().contains("Invalid location."));

    }
    @Test
    public void  unsuccessfulRequestEmptyDescription(){
        requestBody = new PostEventDataFactory(createBodyForPostEvent())
                .setTitle(RandomStringUtils.randomAlphanumeric(8))
                .setImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.goal.com%2Fen-sg%2Fnews%2Fliverpool-vs-manchester-united-lineups-live-updates%2Fbltf4a9e3c54804c6b8&psig=AOvVaw11pYwQiECKpPWu17jL6s6X&ust=1712771074871000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOiy883XtYUDFQAAAAAdAAAAABAE")
                .setDate(dateBuilder.currentDate())
                .setLocation(RandomStringUtils.randomAlphabetic(6))
                .setDescription("")
                .createRequest();

        Response response = new EVPrimeClient()
                .postEvent(requestBody, loginResponseBody.getToken());

        PostUpdateDeleteEventResponse postResponse = response.body().as(PostUpdateDeleteEventResponse.class);

        assertEquals(422, response.statusCode());
        assertTrue(postResponse.getMessage().contains("Adding the event failed due to validation errors."));
        assertTrue(postResponse.getErrors().getDescription().contains("Invalid description."));

    }



}
