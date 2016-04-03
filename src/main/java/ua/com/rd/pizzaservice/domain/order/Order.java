package ua.com.rd.pizzaservice.domain.order;

import ua.com.rd.pizzaservice.domain.order.state.*;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.domain.customer.Customer;

import java.util.List;

public class Order {
    private static Long currentId = 0l;
    private Long id;
    private Customer customer;
    private List<Pizza> pizzaList;
    private Double finalPrice;
    private static State newState = new NewState();
    private static State inProgressState = new InProgressState();
    private static State canceledState = new CanceledState();
    private static State doneState = new DoneState();
    private State currentState;

    public Order(Customer customer, List<Pizza> pizzaList) {
        this.id = currentId++;
        this.customer = customer;
        this.pizzaList = pizzaList;
        currentState = new NewState();
        this.finalPrice = 0d;
        calculatePrice();
    }

    private void calculatePrice(){
        for (Pizza pizza:pizzaList){
            finalPrice+=pizza.getPrice();
        }
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

    public List<Pizza> getPizzaList() {
        return pizzaList;
    }

    public void setPizzaList(List<Pizza> pizzaList) {
        this.finalPrice = 0d;
        this.pizzaList = pizzaList;
        calculatePrice();
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

    public void progress() throws IncorrectStateException {
        if (currentState.equals(newState)) {
            currentState = new InProgressState();
        } else throw new IncorrectStateException("In progress state must be after new state.");
    }

    public void cancel() throws IncorrectStateException {
        if (currentState.equals(inProgressState)){
            currentState = new CanceledState();
        }
        else throw new IncorrectStateException("Canceled state must be after in progress state.");
    }

    public void done() throws IncorrectStateException {
        if (currentState.equals(inProgressState)){
            currentState = new DoneState();
            customer.addCashToCard(getFinalPrice());
        }
        else throw new IncorrectStateException("Done state must be after in progress state.");
    }

    public Integer getPizzasCount(){
        return pizzaList.size();
    }

    public void addPizza(Pizza pizza){
        pizzaList.add(pizza);
        finalPrice+=pizza.getPrice();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", pizzaList=" + pizzaList +
                ", status=" + currentState +
                '}';
    }
}
