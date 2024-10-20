package hibera.api.rtmwebappapi.Auth;

import hibera.api.rtmwebappapi.domain.enums.Role;
import hibera.api.rtmwebappapi.users.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String stripeCustomerId;
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private String country;
    private String state;
    private String description;
    private String companyName;
    private Long companyId;
    private Role role;
}
