package hibera.api.rtmwebappapi.Auth;

import hibera.api.rtmwebappapi.users.Company;
import hibera.api.rtmwebappapi.domain.enums.Role;
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
    private Company company;
    private String phoneNumber;
    private Role role;
}
