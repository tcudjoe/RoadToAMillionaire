package hibera.api.rtmwebappapi.stripe.repository;

import hibera.api.rtmwebappapi.stripe.StripeCustomers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripeCustomerRepository extends JpaRepository<StripeCustomers, Long> {
}
