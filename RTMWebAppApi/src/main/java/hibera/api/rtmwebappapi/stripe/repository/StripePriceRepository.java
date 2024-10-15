package hibera.api.rtmwebappapi.stripe.repository;

import hibera.api.rtmwebappapi.stripe.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripePriceRepository extends JpaRepository<Prices, Long> {
}
