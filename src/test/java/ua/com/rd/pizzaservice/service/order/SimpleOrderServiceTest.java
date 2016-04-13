package ua.com.rd.pizzaservice.service.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.order.InMemOrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.InMemPizzaRepository;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath:repositoryContext.xml"),
        @ContextConfiguration("classpath:appContext.xml")
})
public class SimpleOrderServiceTest {
    private InMemPizzaRepository pizzaRepository;
    private InMemOrderRepository orderRepository;
    @Autowired
    private SimpleOrderService service;
    private Customer customer;

    @Before
    public void setUp(){
        service = new SimpleOrderService();
        orderRepository = new InMemOrderRepository();
        pizzaRepository = new InMemPizzaRepository();
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("repositoryContext.xml");
        pizzaRepository.addPizza((Pizza) context.getBean("pizza1"));
        pizzaRepository.addPizza((Pizza) context.getBean("pizza2"));
        pizzaRepository.addPizza((Pizza) context.getBean("pizza3"));
        pizzaRepository.addPizza((Pizza) context.getBean("pizza4"));
        context.close();
        service.setOrderRepository(orderRepository);
        service.setPizzaRepository(pizzaRepository);
        customer = new Customer(1l,"name",new Address("C","c","s","b"));
    }


    @Test(expected = InvalidPizzasCountException.class)
    public void placeNewOrderNoPizzasShouldRiseException() throws InvalidPizzasCountException {
        service.placeNewOrder(customer);
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void placeNewOrderMoreTenPizzasShouldRiseException() throws InvalidPizzasCountException {
        service.placeNewOrder(customer, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l, 1l);
    }

    @Test
    public void placeNewOrderShouldBeWorked() throws InvalidPizzasCountException {
        int count = orderRepository.getOrders().size();
        service.placeNewOrder(customer, 1l, 1l, 1l);
        ++count;
        assertEquals(count, orderRepository.getOrders().size());
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void addZeroPizzasToOrderShouldRiseException() throws InvalidPizzasCountException {
        Order order = new Order(customer, new ArrayList<Pizza>(){{
            add(pizzaRepository.getPizzaByID(1l));
            add(pizzaRepository.getPizzaByID(3l));
        }}
        );
        service.addPizzasToOrder(order, 2l, 0);
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void addPizzasToOrderSumMoreThanTenShouldRiseException() throws InvalidPizzasCountException {
        Order order = new Order(customer, new ArrayList<Pizza>(){{
            add(pizzaRepository.getPizzaByID(1l));
            add(pizzaRepository.getPizzaByID(3l));
        }}
        );
        service.addPizzasToOrder(order, 2l, 9);
    }

    @Test
    public void addPizzasToOrderShouldBeAdded() throws InvalidPizzasCountException {
        Order order = new Order(customer, new ArrayList<Pizza>(){{
            add(pizzaRepository.getPizzaByID(1l));
            add(pizzaRepository.getPizzaByID(3l));
        }}
        );
        Integer count = order.getPizzasCount();
        service.addPizzasToOrder(order,2l,3);
        count+=3;
        assertEquals(count,order.getPizzasCount());
    }
}
