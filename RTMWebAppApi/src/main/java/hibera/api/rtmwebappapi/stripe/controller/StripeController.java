package hibera.api.rtmwebappapi.stripe.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import hibera.api.rtmwebappapi.stripe.dto.StripeProductsRequestDTO;
import hibera.api.rtmwebappapi.stripe.dto.StripeProductsResponseDTO;
import hibera.api.rtmwebappapi.stripe.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/create/product")
    public ResponseEntity<StripeProductsResponseDTO> createProduct(@RequestBody StripeProductsRequestDTO products) throws StripeException {
        StripeProductsResponseDTO response = stripeService.createProduct(products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create/price")
    public Price createPrice(@RequestParam String productId,
                             @RequestParam long amount,
                             @RequestParam String currency,
                             @RequestParam String interval) throws StripeException {
        Product product = Product.retrieve(productId);
        return stripeService.createPrice(product, amount, currency, interval);
    }
}
