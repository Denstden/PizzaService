package ua.com.rd.pizzaservice.repository.card;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;

import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemAccumulativeCardRepository
        implements AccumulativeCardRepository{
    private Set<AccumulativeCard> cards = new HashSet<>();

    @Override
    public AccumulativeCard addCard(Customer customer) {
        AccumulativeCard card = new AccumulativeCard(customer);
        cards.add(card);
        return card;
    }

    @Override
    public Set<AccumulativeCard> getCards() {
        return cards;
    }

    @Override
    public AccumulativeCard deleteCard(Customer customer)
            throws NoAccumulativeCardException {
        for (AccumulativeCard card : cards) {
            if (isEqualsCustomers(customer, card.getCustomer())) {
                cards.remove(card);
                return card;
            }
        }
        throw new NoAccumulativeCardException();
    }

    private boolean isEqualsCustomers(Customer customer1, Customer customer2) {
        return customer2.getId().equals(customer1.getId());
    }
}
