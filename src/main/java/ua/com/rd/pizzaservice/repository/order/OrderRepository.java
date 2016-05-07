package ua.com.rd.pizzaservice.repository.order;

import ua.com.rd.pizzaservice.domain.order.Order;

import java.util.Set;

public interface OrderRepository {
    Long saveOrder(Order order);

    Order getOrderById(Long id);

    void updateOrder(Order order);

    void deleteOrder(Order order);

    Set<Order> getAllOrders();

    Long countOfOrders();
}
