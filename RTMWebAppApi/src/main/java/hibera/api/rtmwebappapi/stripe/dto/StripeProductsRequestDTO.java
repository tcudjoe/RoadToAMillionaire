package hibera.api.rtmwebappapi.stripe.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeProductsRequestDTO {
    private String name;
    private String product_id;
    private String description;
}
