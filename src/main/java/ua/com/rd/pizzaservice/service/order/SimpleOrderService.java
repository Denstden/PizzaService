package ua.com.rd.pizzaservice.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import java.util.*;

@Service
@Transactional
public class SimpleOrderService implements OrderService {
    private static final Integer MAX_COUNT_OF_PIZZAS_IN_ORDER = 10;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    public SimpleOrderService() {
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setPizzaRepository(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public Order placeNewOrder(Customer customer, Long... pizzasID)
            throws InvalidPizzasCountException {
        if (pizzasID.length>MAX_COUNT_OF_PIZZAS_IN_ORDER || pizzasID.length<1){
            throw new InvalidPizzasCountException();
        }
        Map<Pizza, Integer> pizzas = pizzasByArrOfId(pizzasID);
        Order newOrder = createOrder();
        newOrder.setCustomer(customer);
        newOrder.setPizzas(pizzas);

        orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
        return newOrder;
    }

    @Override
    public boolean addPizzasToOrder(Order order, Long pizzaID, int count)
            throws InvalidPizzasCountException {
        if (count<1 || order.getPizzasCount()+count>MAX_COUNT_OF_PIZZAS_IN_ORDER){
            throw new InvalidPizzasCountException();
        }
        Iterator<Map.Entry<Pizza, Integer>> iterator = pizzasByArrOfId(pizzaID).entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<Pizza, Integer> map =iterator.next();
            order.addPizza(map.getKey(), map.getValue());
        }
        return true;
    }

    @Lookup
    protected Order createOrder() {
        return null;
    }

    private Map<Pizza, Integer> pizzasByArrOfId(Long ... pizzasID) {
        Map<Pizza, Integer> pizzas = new HashMap<>();
        Pizza pizza;
        for(Long id : pizzasID){
            pizza = pizzaRepository.getPizzaByID(id);
            if (pizzas.containsKey(pizza)){
                pizzas.replace(pizza, pizzas.get(pizza)+1);
            }
            else {
                pizzas.put(pizza, 1);  // get Pizza from predifined in-memory list
            }
        }
        return pizzas;
    }
}
