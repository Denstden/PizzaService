package ua.com.rd.pizzaservice.domain.order;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.order.state.*;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.domain.customer.Customer;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ORDERS")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Order {
    @Id
    @SequenceGenerator(name="ORDER_SEQ", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ORDER_SEQ")
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    private Customer customer;

    @Column(name = "FINAL_PRICE")
    private Double finalPrice = 0d;

    @Convert(converter = StateConverter.class)
    private State currentState = newState;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "DONE_DATE")
    private Date doneDate;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    @ElementCollection
    @CollectionTable(name="ORDERS_PIZZAS")
    @MapKeyJoinColumn(name = "PIZZA_ID")
    @Column(name = "COUNT")
    private Map<Pizza, Integer> pizzas = new HashMap<>();

    @Version
    private Integer version;

    private static State newState = new NewState();
    private static State inProgressState = new InProgressState();
    private static State canceledState = new CanceledState();
    private static State doneState = new DoneState();

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<Pizza, Integer> getPizzas() {
        return pizzas;
    }

    public void setPizzas(Map<Pizza, Integer> pizzas) {
        this.pizzas = pizzas;
        calculatePrice();
    }

    private void calculatePrice() {
        finalPrice = 0d;
        for (Map.Entry<Pizza, Integer> entry: pizzas.entrySet()) {
            finalPrice += entry.getKey().getPrice()*entry.getValue();
        }
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void progress() throws IncorrectStateException {
        if (currentState.equals(newState)) {
            currentState = inProgressState;
        } else throw new IncorrectStateException("In progress state " +
                "must be after new state.");
    }

    public void cancel() throws IncorrectStateException {
        if (currentState.equals(inProgressState)) {
            currentState = canceledState;
        } else throw new IncorrectStateException("Canceled state " +
                "must be after in progress state.");
    }

    public void done() throws IncorrectStateException {
        if (currentState.equals(inProgressState)) {
            currentState = doneState;
        } else throw new IncorrectStateException("Done state " +
                "must be after in progress state.");
    }

    public Integer getPizzasCount() {
        return pizzas.size();
    }

    public void addPizza(Pizza pizza, Integer count) {
        if (pizzas.containsKey(pizza)){
            pizzas.replace(pizza, pizzas.get(pizza)+count);
        }
        else {
            pizzas.put(pizza, count);
        }
        finalPrice += pizza.getPrice()*count;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", pizzas=" + pizzas +
                ", status=" + currentState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return id != null && order.getId() != null &&
                id.equals(order.getId()) ||
                finalPrice.equals(order.finalPrice) &&
                        currentState.equals(order.currentState) &&
                        creationDate.equals(order.creationDate) &&
                        (doneDate.equals(order.doneDate));

    }

    @Override
    public int hashCode() {
        int result = 0;

        result = 31 * result + id.hashCode();
        result = 31 * result + finalPrice.hashCode();
        result = 31 * result + currentState.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + doneDate.hashCode();
        return result;
    }
}
