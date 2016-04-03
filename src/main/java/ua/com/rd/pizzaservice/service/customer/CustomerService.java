package ua.com.rd.pizzaservice.service.customer;

import ua.com.rd.pizzaservice.domain.order.Order;

public interface CustomerService {
    void giveCard(Long id);
    boolean payForOrder(Order order);
}
