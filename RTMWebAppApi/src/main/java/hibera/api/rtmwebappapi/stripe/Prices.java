package hibera.api.rtmwebappapi.stripe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stripe_prices")
public class Prices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String price_id; // To store Stripe's price ID
    private BigDecimal priceAmount;
    private String currency;
    private String interval;
    @ManyToOne
    @JoinColumn(name = "product_id") // Foreign key referencing Products
    private Products product;
}
