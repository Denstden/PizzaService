package ua.com.rd.pizzaservice.service.card;

import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.repository.card.AccumulativeCardRepository;

public class AccumulativeCardServiceImpl implements AccumulativeCardService {
    private AccumulativeCardRepository repository;

    public AccumulativeCardServiceImpl(AccumulativeCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccumulativeCard findCard(Customer customer) throws NoAccumulativeCardException {
        for (AccumulativeCard card:repository.getCards()){
            if (customer.getId().equals(card.getCustomer().getId())){
                return card;
            }
        }
        throw new NoAccumulativeCardException();
    }

    @Override
    public AccumulativeCard giveCard(Customer customer) {
        return repository.addCard(customer);
    }

    @Override
    public void deleteCard(Customer customer) {
        for (AccumulativeCard card:repository.getCards()){
            if (customer.getId().equals(card.getCustomer().getId())){
                try {
                    repository.deleteCard(customer);
                } catch (NoAccumulativeCardException ignored) {
                }
            }
        }
    }
}
