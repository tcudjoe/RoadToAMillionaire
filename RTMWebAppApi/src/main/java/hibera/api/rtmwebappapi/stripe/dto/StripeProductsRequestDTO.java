package hibera.api.rtmwebappapi.stripe.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeProductsRequestDTO {
    private String name;
    private String productId;
    private String description;
    private List<PriceRequestDTO> prices;
}
