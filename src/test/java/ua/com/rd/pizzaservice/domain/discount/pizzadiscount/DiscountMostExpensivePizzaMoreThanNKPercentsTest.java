package ua.com.rd.pizzaservice.domain.discount.pizzadiscount;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DiscountMostExpensivePizzaMoreThanNKPercentsTest {
    private DiscountMostExpensivePizzaMoreThanNKPercents discount;

    @Before
    public void setUp() {
        discount = new DiscountMostExpensivePizzaMoreThanNKPercents(2, 50.);
    }

    @Test
    public void calculateDiscountForTwoPizzasShouldBeZero() {
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT), 1);
            put(new Pizza("Pizza 2", 120., Pizza.PizzaType.SEA), 1);
        }};
        Double expected = 0.;
        discount.setPizzas(pizzas);
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void calculateDiscountForThreePizzasShouldBeNotZero() {
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT), 1);
            put(new Pizza("Pizza 2", 120., Pizza.PizzaType.SEA), 1);
            put(new Pizza("Pizza 3", 110., Pizza.PizzaType.MEAT), 1);
        }};
        discount.setPizzas(pizzas);
        Double expected = 60.;
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void calculateDiscountForSixPizzasShouldBeNotZero() {
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT), 3);
            put(new Pizza("Pizza 2", 120., Pizza.PizzaType.SEA), 2);
            put(new Pizza("Pizza 3", 110., Pizza.PizzaType.MEAT), 1);
        }};
        discount.setPizzas(pizzas);
        Double expected = 60.;
        assertEquals(expected, discount.calculate());
    }
}
