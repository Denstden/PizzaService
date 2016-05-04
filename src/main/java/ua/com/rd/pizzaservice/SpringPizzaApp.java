package ua.com.rd.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;
import ua.com.rd.pizzaservice.service.customer.CustomerService;
import ua.com.rd.pizzaservice.service.order.InvalidPizzasCountException;

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
        //System.out.println(customerService.findAll());
        /*PizzaService pizzaService = context.getBean("pizzaServiceImpl", PizzaService.class);
        System.out.println(pizzaService.findAll());*/
        AccumulativeCardService cardService = context.getBean(
                "accumulativeCardServiceImpl", AccumulativeCardService.class);
        //AccumulativeCard card = new AccumulativeCard(customerService.getCustomerById(19L));
        //cardService.createCard(card);
        //card = cardService.findCard(customerService.getCustomerById(19L));
        //cardService.deleteCard(card);
        Customer customer = customerService.getCustomerById(19L);
        AccumulativeCard card = cardService.findCard(customer);
        System.out.println(cardService.deleteCard(card));
        //cardService.createCard(card);//do not work with @Version in Customer
       /* OrderService orderService = context.getBean("simpleOrderService", OrderService.class);
        Order order = orderService.placeNewOrder(customerService.getCustomerById(19L), 18L, 18L, 25L);
        System.out.println(order);*/


        context.close();
        repositoryContext.close();

    }
}
