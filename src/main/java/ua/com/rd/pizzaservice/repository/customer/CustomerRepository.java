package ua.com.rd.pizzaservice.repository.customer;

import ua.com.rd.pizzaservice.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer getCustomerById(Long id);
    List<Customer> findAll();
}
