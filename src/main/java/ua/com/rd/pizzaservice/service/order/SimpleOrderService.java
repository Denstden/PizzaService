package ua.com.rd.pizzaservice.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
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
        List<Pizza> pizzas = pizzasByArrOfId(pizzasID);
        Order newOrder = createOrder();
        newOrder.setCustomer(customer);
        newOrder.setPizzaList(pizzas);

        orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
        return newOrder;
    }

    @Override
    public boolean addPizzasToOrder(Order order, Long pizzaID, int count)
            throws InvalidPizzasCountException {
        if (count<1 || order.getPizzasCount()+count>MAX_COUNT_OF_PIZZAS_IN_ORDER){
            throw new InvalidPizzasCountException();
        }
        for (int i=0;i<count;i++){
            order.addPizza(pizzasByArrOfId(pizzaID).get(0));
        }
        return true;
    }

    @Lookup
    protected Order createOrder() {
        return null;
    }

    private List<Pizza> pizzasByArrOfId(Long ... pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();

        for(Long id : pizzasID){
            pizzas.add(pizzaRepository.getPizzaByID(id));  // get Pizza from predifined in-memory list
        }
        return pizzas;
    }
}
