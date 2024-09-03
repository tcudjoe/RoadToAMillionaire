package hibera.api.rtmwebappapi.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    public Product createProduct(String name, String description) throws StripeException {
        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName(name)
                .setDescription(description)
                .build();

        return Product.create(productParams);
    }

    public Price createPrice(Product product, long amount, String currency, String interval) throws StripeException {
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency(currency)
                .setUnitAmount(amount)
                .setRecurring(PriceCreateParams.Recurring.builder()
                        .setInterval(PriceCreateParams.Recurring.Interval.valueOf(interval.toUpperCase()))
                        .build())
                .setProduct(product.getId())
                .build();

        return Price.create(priceParams);
    }
}
