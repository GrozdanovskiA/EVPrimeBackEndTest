package positive;

import client.EVPrimeClient;
import io.restassured.response.Response;
import models.response.GetEventsResponse;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GetAllEventsTests {


    @Test
    public void getAllEventsTest(){
        Response response = new EVPrimeClient()
                .getAllEvents();

        GetEventsResponse responseBody = response.body().as(GetEventsResponse.class);

        assertEquals(200,response.statusCode());
        assertFalse(responseBody.getEvents().isEmpty());
    }
}
