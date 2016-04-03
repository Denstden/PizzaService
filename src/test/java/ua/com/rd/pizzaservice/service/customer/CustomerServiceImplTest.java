package ua.com.rd.pizzaservice.service.customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.order.state.DoneState;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.customer.InMemCustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {
    @Spy
    private InMemCustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Before
    public void setUp(){
        customerRepository = new InMemCustomerRepository();
        customerService = new CustomerServiceImpl(customerRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void giveCardShouldCallOneTimesMethodInRepository(){
        Long id = 1l;
        customerService.giveCard(id);
        verify(customerRepository, times(1)).getCustomerById(id);
    }

    @Test
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
    }
}
