package negative;

import client.EVPrimeClient;
import io.restassured.response.Response;
import models.response.GetEventsResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import util.DateBuilder;

import static org.junit.Assert.*;

public class GetSingleEventNegativeTest {

    DateBuilder dateBuilder= new DateBuilder();

    @Test
    public void getSingleEventWrongId() {

        Response response = new EVPrimeClient()
                .getEventById(RandomStringUtils.randomAlphanumeric(10) + dateBuilder.currentDateAndTime());

        GetEventsResponse responseBody = response.body().as(GetEventsResponse.class);

        assertEquals(200,response.statusCode());
        assertTrue(responseBody.getEvents().isEmpty());
    }
}
