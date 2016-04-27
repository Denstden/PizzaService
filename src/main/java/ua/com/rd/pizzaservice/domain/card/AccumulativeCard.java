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
    private Double cash = 0.;

    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Version
    private Integer version;

    public AccumulativeCard() {
    }

    public AccumulativeCard(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccumulativeCard card = (AccumulativeCard) o;
        if (id!=null && card.getId()!=null && id.equals(card.getId())){
            return true;
        }

        if (!cash.equals(card.cash)) return false;
        return customer.equals(card.customer);

    }

    @Override
    public int hashCode() {
        int result = cash.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + customer.hashCode();
        return result;
    }
}