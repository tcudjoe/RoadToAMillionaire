package hibera.api.rtmwebappapi.stripe;

import hibera.api.rtmwebappapi.domain.Company;
import hibera.api.rtmwebappapi.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stripe_subscriptions")
public class Subscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subscription_id;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String stripe_subscription_id;
    private String stripe_customer_id;
    @ManyToOne
    @JoinColumn(name = "susbcription_plan_id", nullable = false)
    private SubscriptionPlans subscription_plan;
    private LocalDate subscription_start_date;
    private LocalDate subscription_end_date;
    private LocalDate subscription_creation_date;
    private LocalDate subscription_last_updated;
}
