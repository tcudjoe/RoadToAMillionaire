package hibera.api.rtmwebappapi.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import hibera.api.rtmwebappapi.stripe.Products;
import hibera.api.rtmwebappapi.stripe.dto.PriceRequestDTO;
import hibera.api.rtmwebappapi.stripe.dto.StripeProductsRequestDTO;
import hibera.api.rtmwebappapi.stripe.dto.StripeProductsResponseDTO;
import hibera.api.rtmwebappapi.stripe.repository.StripePriceRepository;
import hibera.api.rtmwebappapi.stripe.repository.StripeProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StripeProductService {

    @Autowired
    private StripeProductRepository productRepository;
    @Autowired
    private StripePriceRepository priceRepository;
    @Autowired
    private StripePriceService priceService;

    public StripeProductsResponseDTO createProduct(StripeProductsRequestDTO requestDTO) throws StripeException {
        // Build Stripe product creation parameters
        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName(requestDTO.getName())
                .setDescription(requestDTO.getDescription())
                .build();

        // Create product on Stripe
        Product stripeProduct = Product.create(productParams);

        // Save the product in the local database
        Products product = new Products();
        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setProductId(stripeProduct.getId());  // Set the Stripe product ID
        productRepository.save(product);

        // If prices are included in the request, process them
        if (requestDTO.getPrices() != null) {
            // Retrieve product from the local database
            Products stripeProductId = productRepository.findByProductId(stripeProduct.getId());

            System.out.println("Product ID: " + stripeProductId.getProductId());

            for (PriceRequestDTO priceDTO : requestDTO.getPrices()) {
                // Create price on Stripe
                Price stripePrice = priceService.createStripePrice(
                        stripeProductId.getProductId(),  // Directly use the Stripe product ID
                        priceDTO.getPriceAmount(),
                        priceDTO.getCurrency(),
                        priceDTO.getInterval()
                );

//                // Save the price to the local database
//                PriceRequestDTO localPrice = new PriceRequestDTO();
//                localPrice.setProductId(stripeProductId.getProductId());  // Associate with local product entity
//                localPrice.setPrice_id(stripePrice.getId());  // Set Stripe price ID
//                localPrice.setPriceAmount(priceDTO.getPriceAmount());
//                localPrice.setCurrency(priceDTO.getCurrency());
//                localPrice.setInterval(priceDTO.getInterval());  // Fix this to use priceDTO interval

                // Save the price to the local DB
                priceService.createPriceInDB(stripePrice, stripeProductId, priceDTO);
//                priceService.createPriceInDB(localPrice);
            }
        }


        // Convert the saved product to the response DTO
        return domainToDto(product, stripeProduct);
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
