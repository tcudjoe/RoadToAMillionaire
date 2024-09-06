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
public class CompanyResponse {
    private long companyId;
    private String companyName;
    private String companyAddress;
    private String companyPhonenumber;
    private String companyEmail;
    private String companyKvkNumber;
    private String companyBtwNumber;
    private LocalDateTime companyCreationDate;
    private LocalDateTime companyLastUpdated;
}
