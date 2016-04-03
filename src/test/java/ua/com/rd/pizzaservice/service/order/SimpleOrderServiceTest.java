package ua.com.rd.pizzaservice.service.order;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.order.InMemOrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.InMemPizzaRepository;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SimpleOrderServiceTest {
    private SimpleOrderService service;
    private Customer customer;

    @Before
    public void setUp(){
        service = new SimpleOrderService(new InMemOrderRepository(), new InMemPizzaRepository());
        customer = new Customer(1l,"name",new Address("C","c","s","b"));
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void placeNewOrderNoPizzasShouldRiseException() throws InvalidPizzasCountException {
        service.placeNewOrder(customer);
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void placeNewOrderMoreTenPizzasShouldRiseException() throws InvalidPizzasCountException {
        service.placeNewOrder(customer, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l);
    }

    @Test
    public void placeNewOrderShouldBeSavedToRepository() throws InvalidPizzasCountException {
        Long countOfOrders = service.getOrderRepository().countOfOrders();
        service.placeNewOrder(customer, 1l, 1l, 1l, 1l, 1l, 1l);
        assertEquals(++countOfOrders, service.getOrderRepository().countOfOrders());
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void addZeroPizzasToOrderShouldRiseException() throws InvalidPizzasCountException {
        Order order = new Order(customer, new ArrayList<Pizza>(){{
            add(service.getPizzaRepository().getPizzaByID(1l));
            add(service.getPizzaRepository().getPizzaByID(3l));
        }}
        );
        service.addPizzasToOrder(order, 2l, 0);
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void addPizzasToOrderSumMoreThanTenShouldRiseException() throws InvalidPizzasCountException {
        Order order = new Order(customer, new ArrayList<Pizza>(){{
            add(service.getPizzaRepository().getPizzaByID(1l));
            add(service.getPizzaRepository().getPizzaByID(3l));
        }}
        );
        service.addPizzasToOrder(order,2l,9);
    }

    @Test
    public void addPizzasToOrderShouldBeAdded() throws InvalidPizzasCountException {
        Order order = new Order(customer, new ArrayList<Pizza>(){{
            add(service.getPizzaRepository().getPizzaByID(1l));
            add(service.getPizzaRepository().getPizzaByID(3l));
        }}
        );
        Integer count = order.getPizzasCount();
        service.addPizzasToOrder(order,2l,3);
        count+=3;
        assertEquals(count,order.getPizzasCount());
    }
}
