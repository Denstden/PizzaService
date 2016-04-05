package ua.com.rd.pizzaservice.service.card;

import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;

public interface AccumulativeCardService {
    AccumulativeCard findCard(Customer customer) throws NoAccumulativeCardException;
    AccumulativeCard giveCard(Customer customer);
    void deleteCard(Customer customer);
}
