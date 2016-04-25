package ua.com.rd.pizzaservice.repository.card;

import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;

import java.util.Set;

public interface AccumulativeCardRepository {
    AccumulativeCard addCard(Customer customer);
    Set<AccumulativeCard> getCards();
    void updateCard(AccumulativeCard card);
    AccumulativeCard getCardById(Long id);
    AccumulativeCard deleteCard(Customer customer);
    AccumulativeCard findCardByCustomer(Customer customer) throws NoAccumulativeCardException;
}
