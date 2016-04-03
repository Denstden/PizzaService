package ua.com.rd.pizzaservice.repository.order;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InMemOrderRepositoryTest {
    private InMemOrderRepository repository;

    @Before
    public void setUp(){
        repository = new InMemOrderRepository();
    }

    @Test
    public void saveOrder(){
        Customer customer = new Customer(1l,"name", new Address("C","c","st","b"));
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza(1l, "Margarita", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(2l, "Barbecue", 120., Pizza.PizzaType.MEAT));
            add(new Pizza(3l, "Four seasons", 130., Pizza.PizzaType.VEGETARIAN));
        }};
        Order order = new Order(customer,pizzas);
        Integer count = repository.getOrders().size();
        assertEquals(order.getId(), repository.saveOrder(order));
        assertEquals(count+1,repository.getOrders().size());
    }

    @Test
    public void countOfOrders(){
        Customer customer = new Customer(1l,"name", new Address("C","c","st","b"));
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza(1l, "Margarita", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(2l, "Barbecue", 120., Pizza.PizzaType.MEAT));
            add(new Pizza(3l, "Four seasons", 130., Pizza.PizzaType.VEGETARIAN));
        }};
        Order order = new Order(customer,pizzas);
        repository.saveOrder(order);
        assertEquals(Long.valueOf(1), repository.countOfOrders());
    }
}
