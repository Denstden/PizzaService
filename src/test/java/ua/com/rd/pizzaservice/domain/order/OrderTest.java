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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    private Order order;

    @Before
    public void setUp(){
        List<Pizza> pizzaList = new ArrayList<Pizza>(){{
            add(new Pizza(1l, "Margarita", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(2l, "Barbecue", 120., Pizza.PizzaType.MEAT));
            add(new Pizza(3l, "Four seasons", 130., Pizza.PizzaType.VEGETARIAN));
            add(new Pizza(4l, "Sea pizza", 150., Pizza.PizzaType.SEA));
        }};
        order = new Order(new Customer(1l,"Customer",new Address("C","c","str","b")), pizzaList);
    }

    @Test
    public void newOrderShouldHasStatusNew(){
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
    public void addPizzaShouldBeIncrementOneTime(){
        Integer count = order.getPizzasCount();
        order.addPizza(new Pizza(5l, "Barbecue2", 130., Pizza.PizzaType.MEAT));
        assertEquals(++count, order.getPizzasCount());
    }
}
