package ua.com.rd.pizzaservice.service.pizza;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.service.ServiceTestUtils;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/inMemDbRepositoryContext.xml",
        "classpath:/appContext.xml",
})
public class PizzaServiceImplInMemDbTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private PizzaService pizzaService;

    private ServiceTestUtils serviceTestUtils;

    @Before
    public void setUp() {
        serviceTestUtils = new ServiceTestUtils(jdbcTemplate);
    }

    @Test
    public void getPizzaByIdTest() {
        Pizza pizza = serviceTestUtils.createPizza(1L, "Margarita", 150., Pizza.PizzaType.MEAT);

        Pizza newPizza = pizzaService.getPizzaById(pizza.getId());
        Assert.assertEquals(pizza, newPizza);
    }

    @Test
    public void savePizzaTest() {
        Pizza pizza = new Pizza();
        pizza.setName("Margarita");
        pizza.setPrice(150.);
        pizza.setType(Pizza.PizzaType.MEAT);
        pizzaService.savePizza(pizza);
        Assert.assertNotNull(pizza.getId());
    }

    @Test
    public void deletePizzaTest() {
        Pizza pizza = serviceTestUtils.createPizza(1L, "Margarita", 150., Pizza.PizzaType.MEAT);
        pizzaService.deletePizza(pizza);

        int countOfRecords = getCountOfPizzasById(pizza.getId());
        Assert.assertEquals(0, countOfRecords);
    }

    private int getCountOfPizzasById(Long id) {
        final String sql = "SELECT COUNT(*) FROM pizzas WHERE pizza_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    @Test
    public void findAllTest() {
        Pizza pizza1 = serviceTestUtils.createPizza(1L, "Margarita1", 150., Pizza.PizzaType.MEAT);
        Pizza pizza2 = serviceTestUtils.createPizza(2L, "Margarita2", 190., Pizza.PizzaType.SEA);
        Pizza pizza3 = serviceTestUtils.createPizza(3L, "Margarita3", 120., Pizza.PizzaType.VEGETARIAN);
        Pizza pizza4 = serviceTestUtils.createPizza(4L, "Margarita4", 130., Pizza.PizzaType.MEAT);

        Set<Pizza> pizzas = pizzaService.findAll();
        Assert.assertEquals(4, pizzas.size());

        Set<Pizza> expected = new HashSet<>();
        expected.add(pizza1);
        expected.add(pizza2);
        expected.add(pizza3);
        expected.add(pizza4);
        Assert.assertEquals(expected, pizzas);
    }
}
