package ua.com.rd.pizzaservice.service.customer;

import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;

import java.util.Set;

public interface CustomerService {
    Customer getCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    void updateCustomer(Customer customer);

    Customer deleteCustomer(Customer customer);

    /*Set<Order> getCustomersOrders(Customer customer);*/
    Set<Customer> findAll();

    boolean payForOrder(Order order);
}
