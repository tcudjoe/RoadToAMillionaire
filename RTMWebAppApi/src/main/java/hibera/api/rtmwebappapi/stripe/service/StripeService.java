package hibera.api.rtmwebappapi.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import hibera.api.rtmwebappapi.stripe.Products;
import hibera.api.rtmwebappapi.stripe.dto.StripeProductsRequestDTO;
import hibera.api.rtmwebappapi.stripe.dto.StripeProductsResponseDTO;
import hibera.api.rtmwebappapi.stripe.repository.StripeProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    @Autowired
    private StripeProductRepository productRepository;

    public StripeProductsResponseDTO createProduct(StripeProductsRequestDTO requestDTO) throws StripeException {
        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName(requestDTO.getName())
                .setDescription(requestDTO.getDescription())
                .build();

        // Create product on Stripe
        Product stripeProduct = Product.create(productParams);

        // Save product to database before creating it on Stripe
        Products product = new Products();
        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setProduct_id(stripeProduct.getId());
        productRepository.save(product);

//        stripeProduct.getId()
        return domainToDto(product, stripeProduct);
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

    private Products dtoToDomain(StripeProductsRequestDTO productRequestDTO) {
        Products product = new Products();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        return product;
    }

    private StripeProductsResponseDTO domainToDto(Products product, Product stripeProduct) {
        StripeProductsResponseDTO responseDTO = new StripeProductsResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(stripeProduct.getName());
        responseDTO.setProduct_id(stripeProduct.getId());
        responseDTO.setDescription(stripeProduct.getDescription());
        return responseDTO;
    }
}
