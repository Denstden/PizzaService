package ua.com.rd.pizzaservice.repository.customer;

import ua.com.rd.pizzaservice.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    void saveCustomer(Customer customer);
    Customer getCustomerById(Long id);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    List<Customer> findAll();
}
