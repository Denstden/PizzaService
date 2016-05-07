package ua.com.rd.pizzaservice.domain.order;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.state.CanceledState;
import ua.com.rd.pizzaservice.domain.order.state.DoneState;
import ua.com.rd.pizzaservice.domain.order.state.InProgressState;
import ua.com.rd.pizzaservice.domain.order.state.NewState;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    private Order order;

    @Before
    public void setUp() {
        Map<Pizza, Integer> pizzaList = new HashMap<Pizza, Integer>() {{
            put(new Pizza(1L, "Margarita", 180., Pizza.PizzaType.MEAT), 1);
            put(new Pizza(2L, "Barbecue", 120., Pizza.PizzaType.MEAT), 1);
            put(new Pizza(3L, "Four seasons", 130., Pizza.PizzaType.VEGETARIAN), 1);
            put(new Pizza(4L, "Sea pizza", 150., Pizza.PizzaType.SEA), 1);
        }};
        order = new Order();
        order.setCustomer(new Customer(1L, "Customer", new Address("C", "c", "str", "b")));
        order.setPizzas(pizzaList);
    }

    @Test
    public void newOrderShouldHasStatusNew() {
        assertEquals(NewState.class, order.getCurrentState().getClass());
    }

    @Test
    public void progressShouldChangeStatus() throws IncorrectStateException {
        order.progress();
        assertEquals(InProgressState.class, order.getCurrentState().getClass());
    }

    @Test(expected = IncorrectStateException.class)
    public void progressShouldRiseException() throws IncorrectStateException {
        order.setCurrentState(new InProgressState());
        order.progress();
    }

    @Test
    public void cancelShouldChangeStatus() throws IncorrectStateException {
        order.progress();
        order.cancel();
        assertEquals(CanceledState.class, order.getCurrentState().getClass());
    }

    @Test(expected = IncorrectStateException.class)
    public void cancelShouldRiseException() throws IncorrectStateException {
        order.cancel();
    }

    @Test
    public void doneShouldChangeStatus() throws IncorrectStateException {
        order.progress();
        order.done();
        assertEquals(DoneState.class, order.getCurrentState().getClass());
    }

    @Test(expected = IncorrectStateException.class)
    public void doneShouldRiseException() throws IncorrectStateException {
        order.done();
    }

    @Test
    public void addPizzaShouldBeIncrementOneTime() {
        Integer count = order.getPizzasCount();
        order.addPizza(new Pizza(5L, "Barbecue2", 130., Pizza.PizzaType.MEAT), 1);
        assertEquals(++count, order.getPizzasCount());
    }
}
