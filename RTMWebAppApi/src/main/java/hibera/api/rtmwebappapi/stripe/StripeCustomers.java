package hibera.api.rtmwebappapi.stripe;

import hibera.api.rtmwebappapi.domain.Company;
import hibera.api.rtmwebappapi.domain.User;
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
    private long customer_id;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String stripe_customer_id;
    private String customer_email;
    private String customer_name;
    private LocalDateTime customer_creation_date;
}
