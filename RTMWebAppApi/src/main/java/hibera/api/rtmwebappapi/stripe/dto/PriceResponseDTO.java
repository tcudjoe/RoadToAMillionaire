package hibera.api.rtmwebappapi.stripe.dto;

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
public class PriceResponseDTO {
    private long id;
    private String price_id; // To store Stripe's price ID
    private BigDecimal priceAmount;
    private String currency;
    private String interval;
}
