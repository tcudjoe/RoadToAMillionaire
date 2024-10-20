package hibera.api.rtmwebappapi.stripe.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import hibera.api.rtmwebappapi.stripe.Products;
import hibera.api.rtmwebappapi.stripe.dto.PriceRequestDTO;
import hibera.api.rtmwebappapi.stripe.dto.PriceResponseDTO;
import hibera.api.rtmwebappapi.stripe.dto.StripeProductsRequestDTO;
import hibera.api.rtmwebappapi.stripe.dto.StripeProductsResponseDTO;
import hibera.api.rtmwebappapi.stripe.service.StripePriceService;
import hibera.api.rtmwebappapi.stripe.service.StripeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe")
public class StripeController {
    @Autowired
    private StripeProductService productService;

    @Autowired
    private StripePriceService priceService;

    @PostMapping("/create/product")
    public ResponseEntity<StripeProductsResponseDTO> createProduct(@RequestBody StripeProductsRequestDTO products) throws StripeException {
        StripeProductsResponseDTO response = productService.createProduct(products);
        return ResponseEntity.ok(response);
    }


}
