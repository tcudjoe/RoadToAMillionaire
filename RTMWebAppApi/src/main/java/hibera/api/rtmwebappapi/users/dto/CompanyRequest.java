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
public class CompanyRequest {
    private String companyName;
    private String companyAddress;
    private String companyPhonenumber;
    private String companyEmail;
    private String companyKvkNumber;
    private String companyBtwNumber;
}
