package hibera.api.rtmwebappapi.stripe.dto;

import hibera.api.rtmwebappapi.stripe.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceRequestDTO {
    private String price_id; // To store Stripe's price ID
    private BigDecimal priceAmount;
    private String currency;
    private String productId;
    private String interval;
}
