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
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml");

        OrderService orderService = context.getBean("orderService", OrderService.class);
        AccumulativeCardService cardService =
                context.getBean("accumulativeCardService", AccumulativeCardService.class);
        CustomerService customerService = context.getBean("customerService", CustomerService.class);
        DiscountService discountService = context.getBean("discountService", DiscountService.class);

        Order order = orderService.placeNewOrder(customerService.getCustomerById(1l), 1l);//180
        orderService.addPizzasToOrder(order, 2l, 6);//720
        orderService.addPizzasToOrder(order, 3l, 3);//390
        System.out.println("Discounts for order: " + discountService.calculateDiscounts(order));
        cardService.giveCard(customerService.getCustomerById(1l));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + cardService.findCard(order.getCustomer()).getCash());

        order = orderService.placeNewOrder(customerService.getCustomerById(1l), 1l);//180
        orderService.addPizzasToOrder(order, 2l, 6);//720
        orderService.addPizzasToOrder(order, 3l, 3);//390
        System.out.println("Discounts for order: "+discountService.calculateDiscounts(order));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + cardService.findCard(order.getCustomer()).getCash());
    }
}
