package hibera.api.rtmwebappapi.stripe;

import hibera.api.rtmwebappapi.users.Company;
import hibera.api.rtmwebappapi.users.User;
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
@Table(name = "stripe_customers")
public class StripeCustomers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String stripeCustomerId;
    private String customerEmail;
    private String customerFirstName;
    private String customerLastName;
    private String customerPassword;
    private String customerUsername;
    private String customerPhoneNumber;
    private String customerBusinessName;
    private String customerCity;
    private String customerState;
    private String customerCountry;
    private String customerPostalCode;
    private String customerAddressLine1;
    private String customerAddressLine2;
    private String customerDescription;
    private LocalDateTime customerCreationDate;
    private LocalDateTime customerLastUpdated;
}
