package hibera.api.rtmwebappapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import hibera.api.rtmwebappapi.domain.Role;
import lombok.*;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String authenticationToken;
    private String username;


}
