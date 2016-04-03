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

public class DiscountMostExpensivePizzaMoreThanNKPercentsTest {
    private DiscountMostExpensivePizzaMoreThanNKPercents discount;

    @Before
    public void setUp(){
        discount = new DiscountMostExpensivePizzaMoreThanNKPercents(2,50.);
    }

    @Test
    public void calculateDiscountForTwoPizzasShouldBeZero(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 2", 120., Pizza.PizzaType.SEA));
        }};
        Double expected = 0.;
        discount.setOrder(new Order(new Customer(1l, "Ivan", new Address("C","c","str","b")),pizzas));
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void calculateDiscountForThreePizzasShouldBeNotZero(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 2", 200., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 3", 160., Pizza.PizzaType.MEAT));
        }};
        discount.setOrder(new Order(new Customer(1l, "Ivan", new Address("C", "c", "str", "b")), pizzas));
        Double expected = 100.;
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void calculateDiscountForSixPizzasShouldBeNotZero(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 2", 200., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 3", 160., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 4", 120., Pizza.PizzaType.VEGETARIAN));
            add(new Pizza("Pizza 5", 220., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 6", 160., Pizza.PizzaType.MEAT));
        }};
        discount.setOrder(new Order(new Customer(1l, "Ivan", new Address("C", "c", "str", "b")), pizzas));
        Double expected = 110.;
        assertEquals(expected, discount.calculate());
    }

}
