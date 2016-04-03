package ua.com.rd.pizzaservice;

import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;
import ua.com.rd.pizzaservice.repository.customer.InMemCustomerRepository;
import ua.com.rd.pizzaservice.repository.order.InMemOrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.InMemPizzaRepository;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;
import ua.com.rd.pizzaservice.service.customer.CustomerService;
import ua.com.rd.pizzaservice.service.customer.CustomerServiceImpl;
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

        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        OrderService orderService = new SimpleOrderService(orderRepository, pizzaRepository);
        DiscountService discountService = new DiscountServiceImpl();

        order = orderService.placeNewOrder(customerRepository.getCustomerById(1l), 1l);//180
        orderService.addPizzasToOrder(order, 2l, 6);//720
        orderService.addPizzasToOrder(order, 3l, 3);//390
        System.out.println("Discounts for order: " + discountService.calculateDiscounts(order));
        customerService.giveCard(1l);
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + order.getCustomer().getCashOnCard());

        order = orderService.placeNewOrder(customerRepository.getCustomerById(1l), 1l);//180
        orderService.addPizzasToOrder(order, 2l, 6);//720
        orderService.addPizzasToOrder(order, 3l, 3);//390
        System.out.println("Discounts for order: "+discountService.calculateDiscounts(order));
        customerService.payForOrder(order);
        System.out.println("Cash on card: " + order.getCustomer().getCashOnCard());
    }

}
