package ua.com.rd.pizzaservice.service.card;

import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.exception.NoAccumulativeCardException;

import java.util.Set;

public interface AccumulativeCardService {
    AccumulativeCard findCard(Customer customer) throws NoAccumulativeCardException;
    AccumulativeCard createCard(AccumulativeCard card);
    AccumulativeCard deleteCard(AccumulativeCard card);
    Set<AccumulativeCard> findAll();
}
