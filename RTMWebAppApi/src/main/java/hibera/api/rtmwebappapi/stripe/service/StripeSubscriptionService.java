package hibera.api.rtmwebappapi.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import com.stripe.param.SubscriptionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeSubscriptionService {
    public Subscription createSubscription(String customerId, String priceId) throws StripeException {
        SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                .addItem(SubscriptionCreateParams.Item.builder()
                        .setPrice(priceId)
                        .build())
                .setCustomer(customerId)
                .build();

        return Subscription.create(params);
    }
}
