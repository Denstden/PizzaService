package ua.com.rd.pizzaservice.repository.card;

import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;

import java.util.HashSet;
import java.util.Set;

public class InMemAccumulativeCardRepository implements AccumulativeCardRepository{
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
    public AccumulativeCard deleteCard(Customer customer) throws NoAccumulativeCardException {
        for (AccumulativeCard card:cards){
            if (card.getCustomer().getId().equals(customer.getId())){
                cards.remove(card);
                return card;
            }
        }
        throw new NoAccumulativeCardException();
    }
}
