package ua.com.rd.pizzaservice.service.order;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.exception.InvalidPizzasCountException;
import ua.com.rd.pizzaservice.service.ServiceTestUtils;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/inMemDbRepositoryContext.xml",
        "classpath:/appContext.xml",
})
public class SimpleOrderServiceInMemDbTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private OrderService orderService;

    private ServiceTestUtils serviceTestUtils;

    @Before
    public void setUp() {
        serviceTestUtils = new ServiceTestUtils(jdbcTemplate);
    }

    @Test
    public void getOrderByIdTest() {
        Order order = createOrder();
        Order newOrder = orderService.getOrderById(order.getId());
        Assert.assertEquals(order, newOrder);
    }

    private Order createOrder() {
        Pizza pizza1 = serviceTestUtils.createPizza(1L, "Margarita", 150., Pizza.PizzaType.MEAT);
        Pizza pizza2 = serviceTestUtils.createPizza(2L, "Margarita1", 190., Pizza.PizzaType.MEAT);
        Pizza pizza3 = serviceTestUtils.createPizza(3L, "Margarita2", 130., Pizza.PizzaType.SEA);
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Address orderAddress = serviceTestUtils.createAddress(2L, "C", "C", "S", "B");
        Customer customer = serviceTestUtils.createCustomer(1L, "Ivan", address);
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(pizza1, 1);
            put(pizza2, 3);
            put(pizza3, 2);
        }};
        Date creationDate = new Date();
        Date doneDate = new Date();
        return serviceTestUtils.createOrder(1L, pizzas, customer, orderAddress, creationDate, doneDate);
    }

    @Test
    public void placeNewOrderTest() throws InvalidPizzasCountException {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Ivan", address);
        serviceTestUtils.createPizza(1L, "Margarita", 150., Pizza.PizzaType.MEAT);
        serviceTestUtils.createPizza(2L, "Margarita1", 190., Pizza.PizzaType.MEAT);
        Order order = orderService.placeNewOrder(customer, address, 1L, 1L, 2L, 1L, 2L);
        Assert.assertNotNull(order.getId());
    }

    @Test
    public void addPizzasToOrderTest() throws InvalidPizzasCountException {
        Order order = createOrder();

        orderService.addPizzasToOrder(order, 1L, 2);
        int countPizzas = jdbcTemplate.queryForObject("SELECT SUM(o.count) summa FROM orders_pizzas o WHERE order_order_id = ?",
                new Object[]{order.getId()}, (resultSet, i) -> {
                    return resultSet.getInt("summa");
                });
        Assert.assertEquals(countPizzas, 8);
    }

    @Test
    public void deleteOrderTest() {
        Order order = createOrder();
        orderService.deleteOrder(order);

        int countOfRecords = getCountOfOrdersById(order.getId());
        Assert.assertEquals(0, countOfRecords);
    }

    private int getCountOfOrdersById(Long id) {
        final String sql = "SELECT COUNT(*) FROM orders WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    @Test
    public void findAllTest() {
        Pizza pizza1 = serviceTestUtils.createPizza(1L, "Margarita", 150., Pizza.PizzaType.MEAT);
        Pizza pizza2 = serviceTestUtils.createPizza(2L, "Margarita1", 190., Pizza.PizzaType.MEAT);
        Pizza pizza3 = serviceTestUtils.createPizza(3L, "Margarita2", 130., Pizza.PizzaType.SEA);
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Address orderAddress = serviceTestUtils.createAddress(2L, "C", "C", "S", "B");
        Customer customer = serviceTestUtils.createCustomer(1L, "Ivan", address);
        Map<Pizza, Integer> pizzas = new HashMap<Pizza, Integer>() {{
            put(pizza1, 1);
            put(pizza2, 3);
            put(pizza3, 2);
        }};
        Date creationDate = new Date();
        Date doneDate = new Date();
        Order order = serviceTestUtils.createOrder(1L, pizzas, customer, orderAddress, creationDate, doneDate);
        Order order2 = serviceTestUtils.createOrder(2L, pizzas, customer, orderAddress, creationDate, doneDate);
        Order order3 = serviceTestUtils.createOrder(3L, pizzas, customer, orderAddress, creationDate, doneDate);

        Set<Order> orders = orderService.findAll();
        Assert.assertEquals(orders.size(), 3);

        Set<Order> expected = new TreeSet<>(new OrderComparator());
        expected.add(order);
        expected.add(order2);
        expected.add(order3);
        Assert.assertEquals(expected, orders);
    }

    private class OrderComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }
}
