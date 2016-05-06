package ua.com.rd.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.exception.InvalidPizzasCountException;
import ua.com.rd.pizzaservice.exception.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;
import ua.com.rd.pizzaservice.service.customer.CustomerService;
import ua.com.rd.pizzaservice.service.order.OrderService;
import ua.com.rd.pizzaservice.service.pizza.PizzaService;

public class SpringPizzaApp {
    public static void main(String[] args) throws InvalidPizzasCountException, NoAccumulativeCardException {
        ConfigurableApplicationContext repositoryContext =
                new ClassPathXmlApplicationContext("inMemRepositoryContext.xml", "dbRepositoryContext.xml");

//        PizzaRepository repository = repositoryContext.getBean("postgreSQLPizzaRepository", PizzaRepository.class);
        //repository.addPizza(new Pizza("Name", 150., Pizza.PizzaType.MEAT));
        //System.out.println(repository.getPizzaByID(1L));
        /*ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"},
                        repositoryContext);

        OrderService orderService = context.getBean("simpleOrderService", OrderService.class);
        AccumulativeCardService cardService =
                context.getBean("accumulativeCardServiceImpl", AccumulativeCardService.class);
        CustomerService customerService = context.getBean("customerServiceImpl", CustomerService.class);
        DiscountService discountService = context.getBean("discountServiceImpl", DiscountService.class);

        Order order = orderService.placeNewOrder(customerService.getCustomerById(1L), 1L);//total price = 180
        orderService.addPizzasToOrder(order, 2L, 6);//total price = 180+720=900
        orderService.addPizzasToOrder(order, 3L, 3);//total price = 900+390=1290
        System.out.println("Discounts for order: " + discountService.calculateDiscounts(order));
        cardService.createCard(customerService.getCustomerById(1L));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + cardService.findCard(order.getCustomer()).getCash());
        System.out.println();

        order = orderService.placeNewOrder(customerService.getCustomerById(1L), 1L);//total price = 180
        orderService.addPizzasToOrder(order, 2L, 6);//total price = 180+720=900
        orderService.addPizzasToOrder(order, 3L, 3);//total price = 900+390=1290
        System.out.println("Discounts for order: " + discountService.calculateDiscounts(order));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + cardService.findCard(order.getCustomer()).getCash());

        context.close();*/
        /*CustomerRepository customerRepository = repositoryContext.getBean("postgreSQLCustomerRepository", CustomerRepository.class);
        List<Customer> customerList = customerRepository.findAll();
        for (Customer customer:customerList){
            System.out.println(customer.getAddresses());
        }*/
        repositoryContext.getEnvironment().setActiveProfiles("db");
        repositoryContext.refresh();
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"},
                        repositoryContext);
        CustomerService customerService = context.getBean("customerServiceImpl", CustomerService.class);
        PizzaService pizzaService = context.getBean("pizzaServiceImpl", PizzaService.class);
        AccumulativeCardService cardService = context.getBean(
                "accumulativeCardServiceImpl", AccumulativeCardService.class);
        OrderService orderService = context.getBean("simpleOrderService", OrderService.class);
        /*Customer customer = new Customer();
        customer.setName("Ivan");
        customer.setAddresses(new HashSet<Address>(){{
            add(new Address("Country1", "City1", "Street1", "Building1"));
            add(new Address("Country2", "City2", "Street2", "Building2"));
        }});
        customerService.saveCustomer(customer);*/
        //System.out.println(customerService.findAll());

        /*pizzaService.savePizza(new Pizza("Margarita", 150., Pizza.PizzaType.VEGETARIAN));
        pizzaService.savePizza(new Pizza("Margarita1", 180., Pizza.PizzaType.MEAT));
        System.out.println(pizzaService.findAll());*/

        //AccumulativeCard card = new AccumulativeCard(customerService.getCustomerById(70L));
        //cardService.createCard(card);
        //card = cardService.findCard(customerService.getCustomerById(19L));
        //cardService.deleteCard(card);
        Customer customer = customerService.getCustomerById(70L);
        /*Address address = new Address("ASDASD", "ASD", "ASd", "ASd");
        customer.addAddress(address);
        customerService.updateCustomer(customer);*/
       /* AccumulativeCard card = cardService.findCard(customer);
        System.out.println(cardService.deleteCard(card));*/
        //cardService.createCard(card);//do not work with @Version in Customer

        //Order order = orderService.placeNewOrder(customer, customer.getAddresses().iterator().next(), 71L, 71L, 72L);
        //System.out.println(order);


        context.close();
        repositoryContext.close();

    }
}
