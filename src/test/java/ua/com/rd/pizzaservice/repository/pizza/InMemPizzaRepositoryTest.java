package ua.com.rd.pizzaservice.repository.pizza;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InMemPizzaRepositoryTest {
    private InMemPizzaRepository repository;

    @Before
    public void setUp(){
        repository = new InMemPizzaRepository();
    }

    @Test
    public void getPizzaByIdShouldExists(){
        Pizza pizza = new Pizza(5l,"Pizza1",150., Pizza.PizzaType.MEAT);
        repository.getPizzaList().add(pizza);
        assertEquals(pizza, repository.getPizzaByID(5l));
    }

    @Test
    public void getPizzaByIdShouldNotExists(){
        Pizza pizza = new Pizza(5l,"Pizza1",150., Pizza.PizzaType.MEAT);
        repository.getPizzaList().add(pizza);
        assertNull(repository.getPizzaByID(7l));
    }
}
