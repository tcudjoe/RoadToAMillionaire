package hibera.api.rtmwebappapi.stripe.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import hibera.api.rtmwebappapi.stripe.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/create/product")
    public Product createProduct(@RequestParam String name, @RequestParam String description) throws StripeException {
        return stripeService.createProduct(name, description);
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
