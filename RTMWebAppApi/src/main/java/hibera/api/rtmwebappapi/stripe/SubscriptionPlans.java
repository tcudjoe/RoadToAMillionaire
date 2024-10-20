package hibera.api.rtmwebappapi.stripe;

import hibera.api.rtmwebappapi.users.Company;
import hibera.api.rtmwebappapi.users.User;
import hibera.api.rtmwebappapi.stripe.enums.PlanInterval;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    private String stripePlanId;
    private String stripePriceId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
