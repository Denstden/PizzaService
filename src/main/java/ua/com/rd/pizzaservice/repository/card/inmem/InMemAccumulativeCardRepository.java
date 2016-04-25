package ua.com.rd.pizzaservice.repository.card.inmem;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.repository.card.AccumulativeCardRepository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemAccumulativeCardRepository
        implements AccumulativeCardRepository {
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
    public void updateCard(AccumulativeCard card) {
        for (AccumulativeCard card1: cards){
            if (card1.getId().equals(card.getId())){
                card1.setCustomer(card.getCustomer());
                card1.setCash(card.getCash());
            }
        }
    }

    @Override
    public AccumulativeCard getCardById(Long id) {
        for (AccumulativeCard card1: cards){
            if (card1.getId().equals(id)){
                return card1;
            }
        }
        return null;
    }

    @Override
    public AccumulativeCard deleteCard(Customer customer) {
        try {
            AccumulativeCard card = findCardByCustomer(customer);
            cards.remove(card);
            return card;
        } catch (NoAccumulativeCardException e) {
            return null;
        }
    }

    @Override
    public AccumulativeCard findCardByCustomer(Customer customer) throws NoAccumulativeCardException {
        for (AccumulativeCard card: cards){
            if (card.getCustomer().getId().equals(customer.getId())){
                return card;
            }
        }
        throw new NoAccumulativeCardException();
    }
}
