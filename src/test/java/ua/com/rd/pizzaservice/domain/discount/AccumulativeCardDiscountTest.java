package ua.com.rd.pizzaservice.domain.discount;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccumulativeCardDiscountTest {
    private final static Double EPSILON = 0.0000001;
    private AccumulativeCardDiscount discount;

    @Before
    public void setUp(){
        discount = new AccumulativeCardDiscount();
    }

    @Test
    public void calculateDiscountCustomerWithoutCardShouldBeZero(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
        }};
        Customer customer = new Customer(1l, "Ivan", new Address("C", "c", "str", "b"));
        discount.setOrder(new Order(customer, pizzas));
        assertEquals(0d, discount.calculate(), EPSILON);
    }

    @Test
    public void calculateDiscountTenPercentsOfCashOnCardLessThanThirtyPercentsOfPrice(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
        }};
        Customer customer = new Customer(1l, "Ivan", new Address("C", "c", "str", "b"));
        customer.giveCard();
        customer.addCashToCard(200d);
        discount.setOrder(new Order(customer, pizzas));
        assertEquals(20d, discount.calculate(), EPSILON);
    }

    @Test
    public void calculateDiscountTenPercentsOfCashOnCardMoreThanThirtyPercentsOfPrice(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
        }};
        Customer customer = new Customer(1l, "Ivan", new Address("C", "c", "str", "b"));
        customer.giveCard();
        customer.addCashToCard(400d);
        discount.setOrder(new Order(customer, pizzas));
        assertEquals(30d, discount.calculate(), EPSILON);
    }

}
