package ua.com.rd.pizzaservice.repository.card;

import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;

import java.util.Set;

public interface AccumulativeCardRepository {
    AccumulativeCard addCard(AccumulativeCard card);
    Set<AccumulativeCard> getCards();
    void updateCard(AccumulativeCard card);
    AccumulativeCard getCardById(Long id);
    AccumulativeCard deleteCard(AccumulativeCard card);
    AccumulativeCard getCardByCustomer(Customer customer) throws NoAccumulativeCardException;
}
