package hibera.api.rtmwebappapi.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService {
    public PaymentIntent createPaymentIntent(long amount, String currency, String paymentMethodType) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .addPaymentMethodType(paymentMethodType)
                .build();

        return PaymentIntent.create(params);
    }
}
