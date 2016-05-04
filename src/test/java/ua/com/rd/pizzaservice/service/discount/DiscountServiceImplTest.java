package ua.com.rd.pizzaservice.service.discount;

import ua.com.rd.pizzaservice.service.card.AccumulativeCardServiceImpl;

import static org.junit.Assert.assertEquals;

public class DiscountServiceImplTest {
    private static final Double EPSILON = 0.0000001;
    private DiscountServiceImpl discountService;
    private AccumulativeCardServiceImpl accumulativeCardService;

    /*@Before
    public void setUp(){
        accumulativeCardService = new AccumulativeCardServiceImpl();
        accumulativeCardService.setAccumulativeCardRepository(new InMemAccumulativeCardRepository());
        DiscountProvider discountProvider = new DiscountProvider();
        discountProvider.setAccumulativeCardService(accumulativeCardService);
        discountProvider.init();
        discountService = new DiscountServiceImpl();
        discountService.setDiscountProvider(discountProvider);
    }
    @Test
    public void calculateDiscountsShouldBeZero(){
        Customer customer = new Customer(1l,"name", new Address("C","c","st","b"));
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza(1l, "Margarita", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(2l, "Barbecue", 120., Pizza.PizzaType.MEAT));
        }};
        Order order = new Order(customer,pizzas);
        assertEquals(0., discountService.calculateDiscounts(order), EPSILON);
    }

    @Test
    public void calculateDiscountsWithoutAccCardThreePizzas(){
        Customer customer = new Customer(1l,"name", new Address("C","c","st","b"));
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza(1l, "Margarita", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(2l, "Barbecue", 120., Pizza.PizzaType.MEAT));
            add(new Pizza(3l, "Four seasons", 100., Pizza.PizzaType.VEGETARIAN));
        }};
        Order order = new Order(customer,pizzas);
        assertEquals(30., discountService.calculateDiscounts(order), EPSILON);
    }

    @Test
    public void calculateDiscountsWithoutAccCardFivePizzas(){
        Customer customer = new Customer(1l,"name", new Address("C","c","st","b"));
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza(1l, "Margarita", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(2l, "Barbecue", 120., Pizza.PizzaType.MEAT));
            add(new Pizza(3l, "Four seasons", 100., Pizza.PizzaType.VEGETARIAN));
            add(new Pizza(4l, "Margarita2", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(5l, "Barbecue2", 160., Pizza.PizzaType.MEAT));
        }};
        Order order = new Order(customer,pizzas);
        assertEquals(120., discountService.calculateDiscounts(order), EPSILON);
    }

    @Test
    public void calculateDiscountsWithAccCardFivePizzas() throws NoAccumulativeCardException {
        Customer customer = new Customer(1l,"name", new Address("C","c","st","b"));
        accumulativeCardService.createCard(customer);
        accumulativeCardService.findCard(customer).addCash(1000d);
        List<Pizza> pizzas = new ArrayList<Pizza>(){{
            add(new Pizza(1l, "Margarita", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(2l, "Barbecue", 120., Pizza.PizzaType.MEAT));
            add(new Pizza(3l, "Four seasons", 100., Pizza.PizzaType.VEGETARIAN));
            add(new Pizza(4l, "Margarita2", 180., Pizza.PizzaType.MEAT));
            add(new Pizza(5l, "Barbecue2", 160., Pizza.PizzaType.MEAT));
        }};
        Order order = new Order(customer,pizzas);
        assertEquals(220., discountService.calculateDiscounts(order), EPSILON);
    }*/

}
