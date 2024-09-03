package hibera.api.rtmwebappapi.domain;

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
    private long company_id;
    private String company_name;
    private String company_address;
    private String company_phonenumber;
    private String company_email;
    private String company_kvk_number;
    private String company_btw_number;
    private LocalDateTime company_creation_date;
}
