package ua.com.rd.pizzaservice.domain.customer;

import ua.com.rd.pizzaservice.domain.address.Address;

import java.util.Optional;

public class Customer {
    private Long id;
    private String name;
    private Address address;
    private Optional<AccumulativeCard> card;

    public Customer(Long id, String name, Address address) {
        this.address = address;
        this.id = id;
        this.name = name;
        this.card = Optional.empty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<AccumulativeCard> getCard(){
        return card;
    }

    public void giveCard(){
        if (!isCardPresent()) {
            card = Optional.of(new AccumulativeCard());
        }
    }

    public boolean isCardPresent(){
        return card.isPresent();
    }

    public void addCashToCard(Double cash) {
        if (card.isPresent()){
            card.get().addCash(cash);
        }
    }

    public Double getCashOnCard() throws NoAccumulativeCardException {
        if (card.isPresent()) {
            return card.get().getCash();
        }
        throw new NoAccumulativeCardException();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", card=" + card +
                '}';
    }
}
