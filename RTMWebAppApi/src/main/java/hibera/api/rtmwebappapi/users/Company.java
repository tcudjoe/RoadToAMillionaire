package hibera.api.rtmwebappapi.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long companyId;
    private String companyName;
    private String companyAddressLine1;
    private String companyAddressLine2;
    private String companyCity;
    private String companyPostalCode;
    private String companyCountry;
    private String companyState;
    private String companyDescription;
    private String companyPhonenumber;
    private String companyEmail;
    private String companyKvkNumber;
    private String companyBtwNumber;
    private LocalDateTime companyCreationDate;
    private LocalDateTime companyLastUpdated;
}