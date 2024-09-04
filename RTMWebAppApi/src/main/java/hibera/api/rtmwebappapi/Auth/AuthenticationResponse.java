package hibera.api.rtmwebappapi.Auth;

import lombok.*;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String authenticationToken;
    private String username;


}
