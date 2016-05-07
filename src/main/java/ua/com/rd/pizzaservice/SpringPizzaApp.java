package ua.com.rd.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.com.rd.pizzaservice.exception.InvalidPizzasCountException;
import ua.com.rd.pizzaservice.exception.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.service.address.AddressService;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;
import ua.com.rd.pizzaservice.service.customer.CustomerService;
import ua.com.rd.pizzaservice.service.order.OrderService;
import ua.com.rd.pizzaservice.service.pizza.PizzaService;

public class SpringPizzaApp {
    public static void main(String[] args) throws InvalidPizzasCountException, NoAccumulativeCardException {
        ConfigurableApplicationContext repositoryContext =
                new ClassPathXmlApplicationContext("inMemRepositoryContext.xml", "dbRepositoryContext.xml");
        repositoryContext.getEnvironment().setActiveProfiles("db");
        repositoryContext.refresh();
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"},
                        repositoryContext);

        AddressService addressService = context.getBean("addressServiceImpl", AddressService.class);
        CustomerService customerService = context.getBean("customerServiceImpl", CustomerService.class);
        PizzaService pizzaService = context.getBean("pizzaServiceImpl", PizzaService.class);
        AccumulativeCardService cardService = context.getBean(
                "accumulativeCardServiceImpl", AccumulativeCardService.class);
        OrderService orderService = context.getBean("simpleOrderService", OrderService.class);

       /* Address address = new Address("Ukraine", "Kiev", "Lomonosova", "15a");
        addressService.saveAddress(address);*/


        /*Customer customer = new Customer();
        customer.setName("Customer1");
        customerService.saveCustomer(customer);
        Customer customer = customerService.getCustomerById(75L);
        customer.addAddress(addressService.getAddressById(1L));
        customerService.updateCustomer(customer);*/

        /*AccumulativeCard card = new AccumulativeCard();
        card.setCash(120.);
        card.setCustomer(customerService.getCustomerById(75L));
        cardService.createCard(card);
        cardService.addCashToCard(card, 180.);
        System.out.println(cardService.findCard(customerService.getCustomerById(75L)));*/

        /*Pizza pizza1 = new Pizza("Margarita", 180., Pizza.PizzaType.MEAT);
        Pizza pizza2 = new Pizza("Barbecue", 200., Pizza.PizzaType.MEAT);
        Pizza pizza3 = new Pizza("Four seasons", 150., Pizza.PizzaType.SEA);
        pizzaService.savePizza(pizza1);
        pizzaService.savePizza(pizza2);
        pizzaService.savePizza(pizza3);
        System.out.println(pizzaService.findAll());*/

        /*Order order = orderService.placeNewOrder(customerService.getCustomerById(75L),
                addressService.getAddressById(1L), 79L, 80L, 79L, 80L);
        orderService.addPizzasToOrder(order, 79L, 3);
        orderService.addPizzasToOrder(order, 81L, 1);*/
        //System.out.println(orderService.getOrderById(82L));

        context.close();
        repositoryContext.close();

    }
}
