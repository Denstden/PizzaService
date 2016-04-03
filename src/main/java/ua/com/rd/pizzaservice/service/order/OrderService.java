package ua.com.rd.pizzaservice.service.order;

import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;

public interface OrderService {
    Order placeNewOrder(Customer customer, Long... pizzasID) throws InvalidPizzasCountException;
    boolean addPizzasToOrder(Order order, Long pizzaID, int count) throws InvalidPizzasCountException;
}
