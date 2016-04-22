package ua.com.rd.pizzaservice.service.customer;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.order.state.DoneState;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.card.InMemAccumulativeCardRepository;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;
import ua.com.rd.pizzaservice.repository.customer.InMemCustomerRepository;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CustomerServiceImplTest {
    private CustomerServiceImpl customerService;

    @Before
    public void setUp(){
        AccumulativeCardServiceImpl cardService = new AccumulativeCardServiceImpl();
        InMemAccumulativeCardRepository cardRepository = new InMemAccumulativeCardRepository();
        cardService.setAccumulativeCardRepository(cardRepository);
        customerService = new CustomerServiceImpl();
        customerService.setAccumulativeCardService(cardService);
        customerService.setCustomerRepository(new InMemCustomerRepository());
    }

    /*@Test
    public void payForOrderStatusShouldBeDone(){
        Customer customer = new Customer(1l,"name", new Address("C","c","st","b"));
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza(1l, "Margarita", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(2l, "Barbecue", 120., Pizza.PizzaType.MEAT));
            add(new Pizza(3l, "Four seasons", 130., Pizza.PizzaType.VEGETARIAN));
        }};
        Order order = new Order(customer,pizzas);
        customerService.payForOrder(order);
        assertEquals(DoneState.class, order.getCurrentState().getClass());
    }*/
}
