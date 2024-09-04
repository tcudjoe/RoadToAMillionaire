package hibera.api.rtmwebappapi.stripe;

import hibera.api.rtmwebappapi.users.Company;
import hibera.api.rtmwebappapi.users.User;
import hibera.api.rtmwebappapi.stripe.enums.PaymentStatus;
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
@Table(name = "payments")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long payment_id;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String stripe_payment_id;
    private double payment_amount;
    private String payment_valuta;
    private LocalDateTime payment_date;
    private String payment_method;
    private PaymentStatus payment_status;
    private LocalDateTime payment_creation_date;

}
