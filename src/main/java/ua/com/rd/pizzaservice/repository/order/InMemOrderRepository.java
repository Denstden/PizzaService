package ua.com.rd.pizzaservice.repository.order;

import ua.com.rd.pizzaservice.domain.order.Order;

import java.util.ArrayList;
import java.util.List;

public class InMemOrderRepository implements OrderRepository {
    private List<Order> orders;

    public InMemOrderRepository() {
        orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Long saveOrder(Order order) {
        orders.add(order);
        return order.getId();
    }

    @Override
    public Long countOfOrders() {
        return (long) orders.size();
    }
}
