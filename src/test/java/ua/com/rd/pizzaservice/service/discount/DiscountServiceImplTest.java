package ua.com.rd.pizzaservice.service.discount;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.exception.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.repository.card.inmem.InMemAccumulativeCardRepository;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DiscountServiceImplTest {
    private static final Double EPSILON = 0.0000001;
    private DiscountServiceImpl discountService;
    private AccumulativeCardServiceImpl accumulativeCardService;

    @Before
    public void setUp() {
        accumulativeCardService = new AccumulativeCardServiceImpl();
        accumulativeCardService.setAccumulativeCardRepository(new InMemAccumulativeCardRepository());
        DiscountProvider discountProvider = new DiscountProvider();
        discountProvider.setAccumulativeCardService(accumulativeCardService);
        discountProvider.init();
        discountService = new DiscountServiceImpl();
        discountService.setDiscountProvider(discountProvider);
    }

    @Test
    public void calculateDiscountsShouldBeZero() {
        Customer customer = new Customer(1L, "name", new Address("C", "c", "st", "b"));
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza(1L, "Margarita", 180., Pizza.PizzaType.MEAT), 1);
            put(new Pizza(2L, "Barbecue", 120., Pizza.PizzaType.MEAT), 1);
        }};
        Order order = new Order();
        order.setCustomer(customer);
        order.setPizzas(pizzas);
        assertEquals(0., discountService.calculateDiscounts(order), EPSILON);
    }

    @Test
    public void calculateDiscountsWithoutAccCardThreePizzas() {
        Customer customer = new Customer(1L, "name", new Address("C", "c", "st", "b"));
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza(1L, "Margarita", 180., Pizza.PizzaType.MEAT), 1);
            put(new Pizza(2L, "Barbecue", 120., Pizza.PizzaType.MEAT), 2);
        }};
        Order order = new Order();
        order.setCustomer(customer);
        order.setPizzas(pizzas);
        assertEquals(54., discountService.calculateDiscounts(order), EPSILON);
    }

    @Test
    public void calculateDiscountsWithoutAccCardFivePizzas() {
        Customer customer = new Customer(1L, "name", new Address("C", "c", "st", "b"));
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza(1L, "Margarita", 180., Pizza.PizzaType.MEAT), 3);
            put(new Pizza(2L, "Barbecue", 120., Pizza.PizzaType.MEAT), 2);
        }};
        Order order = new Order();
        order.setCustomer(customer);
        order.setPizzas(pizzas);
        assertEquals(144., discountService.calculateDiscounts(order), EPSILON);
    }

    @Test
    public void calculateDiscountsWithAccCardFivePizzas() throws NoAccumulativeCardException {
        Customer customer = new Customer(1L, "name", new Address("C", "c", "st", "b"));
        AccumulativeCard card = new AccumulativeCard();
        card.setCustomer(customer);
        card.setCash(1000D);
        accumulativeCardService.createCard(card);
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(new Pizza(1L, "Margarita", 180., Pizza.PizzaType.MEAT), 3);
            put(new Pizza(2L, "Barbecue", 120., Pizza.PizzaType.MEAT), 2);
        }};
        Order order = new Order();
        order.setCustomer(customer);
        order.setPizzas(pizzas);
        assertEquals(244., discountService.calculateDiscounts(order), EPSILON);
    }
}
