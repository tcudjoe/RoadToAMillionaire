package hibera.api.rtmwebappapi.domain.dto;

import hibera.api.rtmwebappapi.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Role role;
}
