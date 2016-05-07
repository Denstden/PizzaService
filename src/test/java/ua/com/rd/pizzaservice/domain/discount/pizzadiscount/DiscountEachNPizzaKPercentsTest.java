package ua.com.rd.pizzaservice.domain.discount.pizzadiscount;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DiscountEachNPizzaKPercentsTest {
    private DiscountEachNPizzaKPercents discount;

    @Before
    public void setUp() {
        discount = new DiscountEachNPizzaKPercents(4, 50.);
    }

    @Test
    public void calculateDiscountForThreePizzasShouldBeZero() {
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT), 1);
            put(new Pizza("Pizza 2", 120., Pizza.PizzaType.SEA), 1);
            put(new Pizza("Pizza 3", 110., Pizza.PizzaType.MEAT), 1);
        }};
        discount.setPizzas(pizzas);
        Double expected = 0.;
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void calculateDiscountForFourPizzasShouldBeNotZero() {
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT), 1);
            put(new Pizza("Pizza 2", 110., Pizza.PizzaType.SEA), 2);
            put(new Pizza("Pizza 3", 120., Pizza.PizzaType.SEA), 1);
        }};
        discount.setPizzas(pizzas);
        Double expected = 60.;
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void calculateDiscountForSevenPizzasShouldBeNotZero() {
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT), 1);
            put(new Pizza("Pizza 2", 110., Pizza.PizzaType.SEA), 4);
            put(new Pizza("Pizza 3", 120., Pizza.PizzaType.SEA), 2);
        }};
        discount.setPizzas(pizzas);
        Double expected = 60.;
        assertEquals(expected, discount.calculate());
    }

    @Test
    public void getDiscountForEightPizzasShouldBeNotZero() {
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza("Pizza 1", 100., Pizza.PizzaType.MEAT), 1);
            put(new Pizza("Pizza 2", 120., Pizza.PizzaType.SEA), 6);
            put(new Pizza("Pizza 3", 140., Pizza.PizzaType.SEA), 1);
        }};
        discount.setPizzas(pizzas);
        Double expected = 130.;
        assertEquals(expected, discount.calculate());
    }
}
