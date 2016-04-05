package ua.com.rd.pizzaservice;

import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.repository.card.AccumulativeCardRepository;
import ua.com.rd.pizzaservice.repository.card.InMemAccumulativeCardRepository;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;
import ua.com.rd.pizzaservice.repository.customer.InMemCustomerRepository;
import ua.com.rd.pizzaservice.repository.order.InMemOrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.InMemPizzaRepository;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardServiceImpl;
import ua.com.rd.pizzaservice.service.customer.CustomerService;
import ua.com.rd.pizzaservice.service.customer.CustomerServiceImpl;
import ua.com.rd.pizzaservice.service.discount.DiscountProvider;
import ua.com.rd.pizzaservice.service.discount.DiscountService;
import ua.com.rd.pizzaservice.service.discount.DiscountServiceImpl;
import ua.com.rd.pizzaservice.service.order.InvalidPizzasCountException;
import ua.com.rd.pizzaservice.service.order.OrderService;
import ua.com.rd.pizzaservice.service.order.SimpleOrderService;

public class PizzaApp {

    public static void main(String[] args) throws NoAccumulativeCardException, InvalidPizzasCountException {
        Order order;

        CustomerRepository customerRepository = new InMemCustomerRepository();
        PizzaRepository pizzaRepository = new InMemPizzaRepository();
        OrderRepository orderRepository = new InMemOrderRepository();
        AccumulativeCardRepository cardRepository = new InMemAccumulativeCardRepository();


        OrderService orderService = new SimpleOrderService(orderRepository, pizzaRepository);
        AccumulativeCardService cardService = new AccumulativeCardServiceImpl(cardRepository);
        CustomerService customerService = new CustomerServiceImpl(customerRepository, cardService);
        DiscountProvider discountProvider = new DiscountProvider(cardService);
        DiscountService discountService = new DiscountServiceImpl(discountProvider);

        order = orderService.placeNewOrder(customerRepository.getCustomerById(1l), 1l);//180
        orderService.addPizzasToOrder(order, 2l, 6);//720
        orderService.addPizzasToOrder(order, 3l, 3);//390
        System.out.println("Discounts for order: " + discountService.calculateDiscounts(order));
        cardService.giveCard(customerRepository.getCustomerById(1l));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + cardService.findCard(order.getCustomer()).getCash());

        order = orderService.placeNewOrder(customerRepository.getCustomerById(1l), 1l);//180
        orderService.addPizzasToOrder(order, 2l, 6);//720
        orderService.addPizzasToOrder(order, 3l, 3);//390
        System.out.println("Discounts for order: "+discountService.calculateDiscounts(order));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + cardService.findCard(order.getCustomer()).getCash());
    }

}
