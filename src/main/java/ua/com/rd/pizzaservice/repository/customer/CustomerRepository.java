package ua.com.rd.pizzaservice.repository.customer;

import ua.com.rd.pizzaservice.domain.customer.Customer;

import java.util.Set;

public interface CustomerRepository {
    Customer saveCustomer(Customer customer);
    Customer getCustomerById(Long id);
    void updateCustomer(Customer customer);
    Customer deleteCustomer(Customer customer);
    /*Set<Order> getCustomersOrders(Customer customer);*/
    Set<Customer> findAll();
}
