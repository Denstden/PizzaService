package ua.com.rd.pizzaservice.service.order;

import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.exception.InvalidPizzasCountException;

import java.util.Set;

public interface OrderService {
    Order getOrderById(Long id);
    Order placeNewOrder(Customer customer, Address address, Long... pizzasID) throws InvalidPizzasCountException;
    boolean addPizzasToOrder(Order order, Long pizzaID, int count) throws InvalidPizzasCountException;
    void deleteOrder(Order order);
    Set<Order> findAll();
}
