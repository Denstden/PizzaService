package ua.com.rd.pizzaservice.repository.customer;

import ua.com.rd.pizzaservice.domain.customer.Customer;

public interface CustomerRepository {
    Customer getCustomerById(Long id);
}
