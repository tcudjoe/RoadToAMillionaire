package hibera.api.rtmwebappapi.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.param.PriceCreateParams;
import hibera.api.rtmwebappapi.stripe.Prices;
import hibera.api.rtmwebappapi.stripe.Products;
import hibera.api.rtmwebappapi.stripe.dto.PriceRequestDTO;
import hibera.api.rtmwebappapi.stripe.repository.StripePriceRepository;
import hibera.api.rtmwebappapi.stripe.repository.StripeProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePriceService {

    @Autowired
    private StripePriceRepository priceRepository;
    @Autowired
    private StripeProductRepository productRepository;

    public Price createStripePrice(String productId, BigDecimal amount, String currency, String interval) throws StripeException {
        // Create the price on Stripe
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency(currency)
                .setUnitAmount(amount.multiply(BigDecimal.valueOf(100)).longValue()) // Convert to cents
                .setProduct(productId)
                .setRecurring(
                        PriceCreateParams.Recurring.builder()
                                .setInterval(PriceCreateParams.Recurring.Interval.valueOf(interval))
                                .build()
                )
                .build();

        // Create and return the Stripe price object
        return Price.create(priceParams);
    }

    public Prices createPriceInDB(Price stripePrice, Products product, PriceRequestDTO priceDTO) {
        // Create the price entity in the local database
        Prices price = new Prices();
        price.setPriceAmount(priceDTO.getPriceAmount());  // Amount from the DTO
        price.setCurrency(priceDTO.getCurrency());  // Currency from the DTO
        price.setProduct(product);  // Set the actual product entity (local DB reference)
        price.setPrice_id(stripePrice.getId());  // Set Stripe's price ID
        price.setInterval(priceDTO.getInterval());  // Set interval from DTO

        // Save to the database
        return priceRepository.save(price);
    }
}
