package ua.com.rd.pizzaservice.repository.pizza;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.pizza.inmem.InMemPizzaRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InMemPizzaRepositoryTest {
    private InMemPizzaRepository pizzaRepository;

    @Before
    public void setUp() {
        pizzaRepository = new InMemPizzaRepository();
    }

    @Test
    public void getPizzaByIdShouldExists() {
        Pizza pizza = new Pizza(5L, "Pizza1", 150., Pizza.PizzaType.MEAT);
        pizzaRepository.addPizza(pizza);
        assertEquals(pizza, pizzaRepository.getPizzaByID(5L));
    }

    @Test
    public void getPizzaByIdShouldNotExists() {
        Pizza pizza = new Pizza(5L, "Pizza1", 150., Pizza.PizzaType.MEAT);
        pizzaRepository.addPizza(pizza);
        assertNull(pizzaRepository.getPizzaByID(7L));
    }
}
