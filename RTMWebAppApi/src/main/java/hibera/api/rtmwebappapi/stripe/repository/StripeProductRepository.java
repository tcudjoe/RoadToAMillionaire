package hibera.api.rtmwebappapi.stripe.repository;

import hibera.api.rtmwebappapi.stripe.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripeProductRepository extends JpaRepository<Products, Long> {
}
