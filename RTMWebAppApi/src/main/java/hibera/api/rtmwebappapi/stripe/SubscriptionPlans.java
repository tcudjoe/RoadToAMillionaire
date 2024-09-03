package hibera.api.rtmwebappapi.stripe;

import hibera.api.rtmwebappapi.domain.Company;
import hibera.api.rtmwebappapi.domain.User;
import hibera.api.rtmwebappapi.stripe.enums.PlanInterval;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions_plans")
public class SubscriptionPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long plan_id;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String stripe_plan_id;
    private double plan_amount;
    private String plan_valuta;
    private PlanInterval plan_interval;
    private String plan_description;
    private boolean plan_active;
}
