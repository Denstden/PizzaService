package ua.com.rd.pizzaservice.service.order;

import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderService implements OrderService {
    private static final Integer MAX_COUNT_OF_PIZZAS_IN_ORDER = 10;
    private OrderRepository orderRepository;
    private PizzaRepository pizzaRepository;

    public SimpleOrderService(OrderRepository orderRepository, PizzaRepository pizzaRepository) {
        this.orderRepository = orderRepository;
        this.pizzaRepository = pizzaRepository;
    }

    public PizzaRepository getPizzaRepository() {
        return pizzaRepository;
    }

    public void setPizzaRepository(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeNewOrder(Customer customer, Long... pizzasID) throws InvalidPizzasCountException {
        if (pizzasID.length>MAX_COUNT_OF_PIZZAS_IN_ORDER || pizzasID.length<1){
            throw new InvalidPizzasCountException();
        }
        List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
        Order newOrder = createOrder(customer, pizzas);

        orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
        return newOrder;
    }

    @Override
    public boolean addPizzasToOrder(Order order, Long pizzaID, int count) throws InvalidPizzasCountException {
        if (count<1 || order.getPizzasCount()+count>MAX_COUNT_OF_PIZZAS_IN_ORDER){
            throw new InvalidPizzasCountException();
        }
        for (int i=0;i<count;i++){
            order.addPizza(pizzasByArrOfId(pizzaID).get(0));
        }
        return true;
    }

    private Order createOrder(Customer customer, List<Pizza> pizzas) {
        return new Order(customer, pizzas);
    }

    private List<Pizza> pizzasByArrOfId(Long ... pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();

        for(Long id : pizzasID){
            pizzas.add(pizzaRepository.getPizzaByID(id));  // get Pizza from predifined in-memory list
        }
        return pizzas;
    }
}
