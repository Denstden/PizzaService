package ua.com.rd.pizzaservice.service.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.repository.card.AccumulativeCardRepository;

@Service("accumulativeCardService")
public class AccumulativeCardServiceImpl implements AccumulativeCardService {
    @Autowired
    private AccumulativeCardRepository accumulativeCardRepository;

    public AccumulativeCardServiceImpl() {
    }

    public void setAccumulativeCardRepository(AccumulativeCardRepository accumulativeCardRepository) {
        this.accumulativeCardRepository = accumulativeCardRepository;
    }

    @Override
    public AccumulativeCard findCard(Customer customer)
            throws NoAccumulativeCardException {
        for (AccumulativeCard card: accumulativeCardRepository.getCards()){
            if (customer.getId().equals(card.getCustomer().getId())){
                return card;
            }
        }
        throw new NoAccumulativeCardException();
    }

    @Override
    public AccumulativeCard giveCard(Customer customer) {
        return accumulativeCardRepository.addCard(customer);
    }

    @Override
    public void deleteCard(Customer customer) {
        for (AccumulativeCard card: accumulativeCardRepository.getCards()){
            if (customer.getId().equals(card.getCustomer().getId())){
                try {
                    accumulativeCardRepository.deleteCard(customer);
                } catch (NoAccumulativeCardException ignored) {
                }
            }
        }
    }
}
