package hibera.api.rtmwebappapi.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyRequest {
    private String company_name;
    private String company_address;
    private String company_phonenumber;
    private String company_email;
    private String company_kvk_number;
    private String company_btw_number;
}
