package hibera.api.rtmwebappapi.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import hibera.api.rtmwebappapi.Auth.RegisterRequest;
import hibera.api.rtmwebappapi.stripe.StripeCustomers;
import hibera.api.rtmwebappapi.stripe.repository.StripeCustomerRepository;
import hibera.api.rtmwebappapi.users.Company;
import hibera.api.rtmwebappapi.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripeCustomerService {
    @Autowired
    private StripeCustomerRepository customerRepository;

    public Customer createCustomerInStripe(User customer) throws StripeException {
        CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setName(customer.getCompanyName())
                .setDescription("First user for company " + customer.getCompanyName() + " name: " + customer.getFirstName() + " " + customer.getLastName() + ", Company descriptions: " + customer.getDescription())
                .setEmail(customer.getEmail())
                .setPhone(customer.getPhonenumber())
                .setAddress(
                        CustomerCreateParams.Address.builder()
                                .setCity(customer.getCity())
                                .setState(customer.getState())
                                .setCountry(customer.getCountry())
                                .setLine1(customer.getAddressLine1())
                                .setLine2(customer.getAddressLine2())
                                .setPostalCode(customer.getPostalCode())
                                .build()
                )
                .build();

        return Customer.create(customerParams);
    }

    public StripeCustomers createStripeCustomerInDb(RegisterRequest customer, Customer stripeCustomer, User user) {
        // Create the customer entity in the local database
        StripeCustomers customerLocal = new StripeCustomers();
        customerLocal.setUser(user);
        customerLocal.setCompany(customer.getCompanyId());
        customerLocal.setStripeCustomerId(stripeCustomer.getId());
        customerLocal.setCustomerFirstName(customer.getFirstName());
        customerLocal.setCustomerLastName(customer.getLastName());
        customerLocal.setCustomerEmail(customer.getEmail());
        customerLocal.setCustomerPhoneNumber(customer.getPhoneNumber());
        customerLocal.setCustomerDescription(customer.getDescription());
        customerLocal.setCustomerPassword(customer.getPassword());
        customerLocal.setCustomerUsername(customer.getUsername());
        customerLocal.setCustomerBusinessName(customer.getCompanyName());
        customerLocal.setCustomerCity(customer.getCity());
        customerLocal.setCustomerState(customer.getState());
        customerLocal.setCustomerCountry(customer.getCountry());
        customerLocal.setCustomerPostalCode(customer.getPostalCode());
        customerLocal.setCustomerAddressLine1(customer.getAddressLine1());
        customerLocal.setCustomerAddressLine2(customer.getAddressLine2());

        // Save the customer to the local database
        return customerRepository.save(customerLocal);
    }
}
