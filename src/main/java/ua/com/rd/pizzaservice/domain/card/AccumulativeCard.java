package ua.com.rd.pizzaservice.domain.card;

import ua.com.rd.pizzaservice.domain.customer.Customer;

import javax.persistence.*;

@Entity
@Table(name = "ACCUMULATIVE_CARDS")
public class AccumulativeCard {
    @Id
    @SequenceGenerator(name="ACCUMULATIVE_CARD_SEQ", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACCUMULATIVE_CARD_SEQ")
    @Column(name ="ACCUMULATIVE_CARD_ID")
    private Long id;

    @Column(name ="CASH")
    private Double cash;

    @OneToOne
    private Customer customer;

    public AccumulativeCard(Customer customer) {
        this.customer = customer;
        cash = 0.;
    }

    public Double getCash() {
        return cash;
    }

    public void addCash(Double cash) {
        this.cash += cash;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "AccumulativeCard{"
                + "cash=" + cash
                + ", customer=" + customer
                + '}';
    }
}