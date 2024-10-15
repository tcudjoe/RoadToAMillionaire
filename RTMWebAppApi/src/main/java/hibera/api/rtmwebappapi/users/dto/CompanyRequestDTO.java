package hibera.api.rtmwebappapi.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDTO {
    private String companyName;
    private String companyAddress;
    private String companyPhonenumber;
    private String companyEmail;
    private String companyKvkNumber;
    private String companyBtwNumber;
}
