package hibera.api.rtmwebappapi.stripe.controller;

import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.Subscription;
import com.stripe.net.Webhook;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe/webhook")
public class StripeWebhookController {
    private static final String STRIPE_WEBHOOK_SECRET = "your_webhook_secret";

    @PostMapping
    public String handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, STRIPE_WEBHOOK_SECRET);

            if ("invoice.payment_succeeded".equals(event.getType())) {
                EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
                Subscription subscription = (Subscription) deserializer.getObject().orElse(null);

                // Handle successful payment (e.g., update database)
            }

            return "";
        } catch (Exception e) {
            return "Webhook error: " + e.getMessage();
        }
    }
}
