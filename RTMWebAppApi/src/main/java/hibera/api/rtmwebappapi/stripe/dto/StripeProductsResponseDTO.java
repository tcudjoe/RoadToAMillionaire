package hibera.api.rtmwebappapi.stripe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeProductsResponseDTO {
    private long id;
    private String name;
    private String product_id;
    private String description;
}
