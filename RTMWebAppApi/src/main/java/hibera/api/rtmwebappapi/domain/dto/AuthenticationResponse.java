package hibera.api.rtmwebappapi.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String authenticationToken;
    private String username;


}
