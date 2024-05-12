package builder;

import models.requests.SignUpLoginRequest;

public class SignUpBuilder {

    public static SignUpLoginRequest createBodyForSignUp(){
        return SignUpLoginRequest.builder()
                .email("Default@email.com")
                .password("Default password")
                .build();
    }


}
