package ua.com.rd.pizzaservice.domain.card;

import ua.com.rd.pizzaservice.domain.customer.Customer;

public class AccumulativeCard{
    private Double cash;
    private Customer customer;

    public AccumulativeCard(Customer customer) {
        this.customer = customer;
        cash = 0.;
    }

    public Double getCash() {
        return cash;
    }

    public void addCash(Double cash1){
        cash+=cash1;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "AccumulativeCard{" +
                "cash=" + cash +
                ", customer=" + customer +
                '}';
    }
}