package ua.com.rd.pizzaservice.service.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.exception.InvalidPizzasCountException;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;
import ua.com.rd.pizzaservice.repository.order.inmem.InMemOrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;
import ua.com.rd.pizzaservice.repository.pizza.inmem.InMemPizzaRepository;
import ua.com.rd.pizzaservice.service.pizza.PizzaService;
import ua.com.rd.pizzaservice.service.pizza.PizzaServiceImpl;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleOrderServiceTest {
    @Mock
    private PizzaService pizzaService;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private SimpleOrderService orderService;

    @Before
    public void setUp() {
        doReturn(new Pizza(1L, "Margarita", 180., Pizza.PizzaType.MEAT))
                .when(pizzaService).getPizzaById(1L);
        doReturn(new Pizza(2L, "Four seasons", 200., Pizza.PizzaType.VEGETARIAN))
                .when(pizzaService).getPizzaById(2L);
        doReturn(new Pizza(3L, "Sea", 120., Pizza.PizzaType.SEA))
                .when(pizzaService).getPizzaById(3L);
    }

    @Test
    public void placeNewOrderTest() throws InvalidPizzasCountException {
        orderService.placeNewOrder(new Customer(), new Address(), 1L, 3L);
        verify(pizzaService, times(1)).getPizzaById(1L);
        verify(pizzaService, times(1)).getPizzaById(3L);
        verify(pizzaService, never()).getPizzaById(2L);
        verify(orderRepository, times(1)).saveOrder(any());
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void placeNewOrderNoPizzasShouldRiseExceptionTest() throws InvalidPizzasCountException {
        orderService.placeNewOrder(new Customer(), new Address());
        verify(pizzaService, never()).getPizzaById(1L);
        verify(pizzaService, never()).getPizzaById(3L);
        verify(pizzaService, never()).getPizzaById(2L);
        verify(orderRepository, never()).saveOrder(any());
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void placeNewOrderTestGtTenPizzasShouldRiseException() throws InvalidPizzasCountException {
        orderService.placeNewOrder(new Customer(), new Address(), 1L, 2L, 3L, 1L, 1L, 1L, 1L, 2L, 1L, 2L, 3L);
        verify(pizzaService, never()).getPizzaById(1L);
        verify(pizzaService, never()).getPizzaById(3L);
        verify(pizzaService, never()).getPizzaById(2L);
        verify(orderRepository, never()).saveOrder(any());
    }

    @Test
    public void addPizzasToOrderTest() throws InvalidPizzasCountException {
        Order order = mock(Order.class);
        orderService.addPizzasToOrder(order, 1L, 3);
        verify(order, times(1)).addPizza(pizzaService.getPizzaById(1L), 3);
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void addPizzasToOrderCountLtOneShouldRiseExceptionTest() throws InvalidPizzasCountException {
        Order order = mock(Order.class);
        orderService.addPizzasToOrder(order, 1L, 0);
        verify(order, never()).addPizza(any(), anyInt());
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void addPizzasToOrderCountGtTenShouldRiseExceptionTest() throws InvalidPizzasCountException {
        Order order = mock(Order.class);
        orderService.addPizzasToOrder(order, 1L, 11);
        verify(order, never()).addPizza(any(), anyInt());
    }

    @Test(expected = InvalidPizzasCountException.class)
    public void addPizzasToOrderSumGtTenShouldRiseExceptionTest() throws InvalidPizzasCountException {
        SimpleOrderService orderService = new SimpleOrderService();
        OrderRepository orderRepository = new InMemOrderRepository();
        orderService.setOrderRepository(orderRepository);
        PizzaRepository pizzaRepository = new InMemPizzaRepository();
        PizzaServiceImpl pizzaService = new PizzaServiceImpl();
        pizzaService.setPizzaRepository(pizzaRepository);
        pizzaService.savePizza(new Pizza(1L, "Margarita", 180., Pizza.PizzaType.MEAT));
        pizzaService.savePizza(new Pizza(2L, "Four seasons", 200., Pizza.PizzaType.VEGETARIAN));
        pizzaService.savePizza(new Pizza(3L, "Sea", 120., Pizza.PizzaType.SEA));
        orderService.setPizzaService(pizzaService);
        Order order1 = orderService.placeNewOrder(new Customer(), new Address(), 1L, 2L, 2L, 3L);
        order1.setId(1L);
        orderService.addPizzasToOrder(order1, 1L, 7);
    }
}
