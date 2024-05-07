package builder;

import models.requests.PostUpdateEventRequest;

public class PostEventObjectBuilder {
    public static PostUpdateEventRequest createBodyForPostEvent() {
        return PostUpdateEventRequest.builder()
                .title("default title")
                .image("defaulт image")
                .date("07.11.2023")
                .location("default location")
                .description("default description")
                .build();
    }
}
