package ua.com.rd.pizzaservice.domain.discount;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class DiscountEachNPizzaKPercentsTest {
    private DiscountEachNPizzaKPercents discount;

    @Before
    public void setUp(){
        discount = new DiscountEachNPizzaKPercents(4,50.);
    }

    @Test
    public void calculateDiscountForThreePizzasShouldBeZero(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 2", 120., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 3", 110., Pizza.PizzaType.MEAT));
        }};
        discount.setOrder(new Order(new Customer(1l, "Ivan", new Address("C", "c", "str", "b")), pizzas));
        Double expected = 0.;
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void calculateDiscountForFourPizzasShouldBeNotZero(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 2", 110., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 3", 110., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 4", 120., Pizza.PizzaType.SEA));
        }};
        discount.setOrder(new Order(new Customer(1l, "Ivan", new Address("C", "c", "str", "b")), pizzas));
        Double expected = 60.;
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void calculateDiscountForSevenPizzasShouldBeNotZero(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 2", 110., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 3", 110., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 4", 120., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 5", 130., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 6", 140., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 7", 150., Pizza.PizzaType.SEA));
        }};
        discount.setOrder(new Order(new Customer(1l, "Ivan", new Address("C", "c", "str", "b")), pizzas));
        Double expected = 60.;
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void getDiscountForEightPizzasShouldBeNotZero(){
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 2", 110., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 3", 110., Pizza.PizzaType.MEAT));
            add(new Pizza("Pizza 4", 120., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 5", 130., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 6", 140., Pizza.PizzaType.VEGETARIAN));
            add(new Pizza("Pizza 7", 150., Pizza.PizzaType.SEA));
            add(new Pizza("Pizza 8", 160., Pizza.PizzaType.VEGETARIAN));
        }};
        discount.setOrder(new Order(new Customer(1l, "Ivan", new Address("C", "c", "str", "b")), pizzas));
        Double expected = 140.;
        assertEquals(expected, discount.calculate());
    }
}
