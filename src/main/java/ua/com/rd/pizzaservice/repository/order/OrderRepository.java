package ua.com.rd.pizzaservice.repository.order;

import ua.com.rd.pizzaservice.domain.order.Order;

public interface OrderRepository {
    Long saveOrder(Order order);
    Long countOfOrders();
}
