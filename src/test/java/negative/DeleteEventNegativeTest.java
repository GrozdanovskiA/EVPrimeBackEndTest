package negative;

import client.EVPrimeClient;
import data.PostEventDataFactory;
import data.SignUpLoginDataFactory;
import database.DbClient;
import io.restassured.response.Response;
import models.requests.PostUpdateEventRequest;
import models.requests.SignUpLoginRequest;
import models.response.LoginResponse;
import models.response.PostUpdateDeleteEventResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import util.DateBuilder;

import java.sql.SQLException;

import static builder.PostEventObjectBuilder.createBodyForPostEvent;
import static builder.SignUpBuilder.createBodyForSignUp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteEventNegativeTest {
    DbClient dbClient = new DbClient();
    private SignUpLoginRequest signUpRequest;
    private LoginResponse loginResponseBody;
    private PostUpdateEventRequest requestBody;
    private String id;
    DateBuilder dateBuilder = new DateBuilder();

    @Test
    public void deleteEventMissingTokenTest() {
        signUpRequest = new SignUpLoginDataFactory(createBodyForSignUp())
                .setEmail(RandomStringUtils.randomAlphanumeric(10) + dateBuilder.currentDateAndTime() + "@mail.com")
                .setPassword(RandomStringUtils.randomAlphanumeric(10))
                .createRequest();

        new EVPrimeClient()
                .signUp(signUpRequest);

        Response loginResponse = new EVPrimeClient()
                .login(signUpRequest);

        loginResponseBody = loginResponse.body().as(LoginResponse.class);

        requestBody = new PostEventDataFactory(createBodyForPostEvent())
                .setTitle(RandomStringUtils.randomAlphanumeric(8))
                .setImage("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.goal.com%2Fen-sg%2Fnews%2Fliverpool-vs-manchester-united-lineups-live-updates%2Fbltf4a9e3c54804c6b8&psig=AOvVaw11pYwQiECKpPWu17jL6s6X&ust=1712771074871000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCOiy883XtYUDFQAAAAAdAAAAABAE")
                .setDate(dateBuilder.currentDate())
                .setLocation(RandomStringUtils.randomAlphabetic(6))
                .setDescription(RandomStringUtils.randomAlphabetic(6))
                .createRequest();

        Response response = new EVPrimeClient()
                .postEvent(requestBody, loginResponseBody.getToken());

          PostUpdateDeleteEventResponse postResponse = response.body().as(PostUpdateDeleteEventResponse.class);
          id = postResponse.getMessage().substring(39);

          Response responseDelete = new EVPrimeClient().deleteEvent("", id);

        PostUpdateDeleteEventResponse deleteResponseBody = responseDelete.body().as(PostUpdateDeleteEventResponse.class);

        assertEquals(401, responseDelete.statusCode());
        assertTrue(deleteResponseBody.getMessage().contains("Not authenticated."));
    }
    @After
    public void deleteEvent() throws SQLException {
        assertTrue(new DbClient()
                .isEventDeletedFromDb(id));
    }

}
