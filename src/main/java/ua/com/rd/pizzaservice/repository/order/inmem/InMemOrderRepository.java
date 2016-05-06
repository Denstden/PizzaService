package ua.com.rd.pizzaservice.repository.order.inmem;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
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
    public Order getOrderById(Long id) {
        for (Order order:orders){
            if (order.getId().equals(id)){
                return order;
            }
        }
        return null;
    }

    @Override
    public void updateOrder(Order order) {
        for (Order order1:orders){
            if (order1.getId().equals(order.getId())){
                order1.setPizzas(order.getPizzas());
                order1.setCustomer(order.getCustomer());
                order1.setCreationDate(order.getCreationDate());
                order1.setDoneDate(order.getDoneDate());
                order1.setCurrentState(order.getCurrentState());
                order1.setFinalPrice(order.getFinalPrice());
            }
        }
    }

    @Override
    public void deleteOrder(Order order) {
        orders.remove(order);
    }

    @Override
    public Set<Order> getAllOrders() {
        return new HashSet<>(orders);
    }

    @Override
    public Long countOfOrders() {
        return (long) orders.size();
    }
}
