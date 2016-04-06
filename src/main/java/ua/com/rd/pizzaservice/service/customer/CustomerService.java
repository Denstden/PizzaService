package ua.com.rd.pizzaservice.service.customer;

import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;

import java.util.List;

public interface CustomerService {
    boolean payForOrder(Order order);
    Customer getCustomerById(Long id);
    List<Customer> findAll();
}
