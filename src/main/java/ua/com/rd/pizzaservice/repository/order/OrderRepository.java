package ua.com.rd.pizzaservice.repository.order;

import ua.com.rd.pizzaservice.domain.order.Order;

import java.util.List;

public interface OrderRepository {
    Long saveOrder(Order order);
    Order getOrderById(Long id);
    void updateOrder(Order order);
    void deleteOrder(Order order);
    List<Order> getAllOrders();
    Long countOfOrders();
}
