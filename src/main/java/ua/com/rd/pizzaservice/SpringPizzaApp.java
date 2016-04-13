package ua.com.rd.pizzaservice;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;
import ua.com.rd.pizzaservice.service.customer.CustomerService;
import ua.com.rd.pizzaservice.service.discount.DiscountService;
import ua.com.rd.pizzaservice.service.order.InvalidPizzasCountException;
import ua.com.rd.pizzaservice.service.order.OrderService;

public class SpringPizzaApp {
    public static void main(String[] args) throws InvalidPizzasCountException, NoAccumulativeCardException {
        ConfigurableApplicationContext repositoryContext =
                new ClassPathXmlApplicationContext("repositoryContext.xml");
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"appContext.xml"},
                        repositoryContext);

        OrderService orderService = context.getBean("simpleOrderService", OrderService.class);
        AccumulativeCardService cardService =
                context.getBean("accumulativeCardServiceImpl", AccumulativeCardService.class);
        CustomerService customerService = context.getBean("customerServiceImpl", CustomerService.class);
        DiscountService discountService = context.getBean("discountServiceImpl", DiscountService.class);

        Order order = orderService.placeNewOrder(customerService.getCustomerById(1l), 1l);//total price = 180
        orderService.addPizzasToOrder(order, 2l, 6);//total price = 180+720=900
        orderService.addPizzasToOrder(order, 3l, 3);//total price = 900+390=1290
        System.out.println("Discounts for order: " + discountService.calculateDiscounts(order));
        cardService.giveCard(customerService.getCustomerById(1l));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + cardService.findCard(order.getCustomer()).getCash());
        System.out.println();

        order = orderService.placeNewOrder(customerService.getCustomerById(1l), 1l);//total price = 180
        orderService.addPizzasToOrder(order, 2l, 6);//total price = 180+720=900
        orderService.addPizzasToOrder(order, 3l, 3);//total price = 900+390=1290
        System.out.println("Discounts for order: " + discountService.calculateDiscounts(order));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + cardService.findCard(order.getCustomer()).getCash());

        context.close();
        repositoryContext.close();
    }
}
