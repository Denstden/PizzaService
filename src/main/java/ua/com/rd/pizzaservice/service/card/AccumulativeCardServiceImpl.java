package ua.com.rd.pizzaservice.service.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.exception.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.repository.card.AccumulativeCardRepository;

import java.util.Set;

@Service
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
        return accumulativeCardRepository.getCardByCustomer(customer);
    }

    @Override
    @Transactional
    public AccumulativeCard createCard(AccumulativeCard card) {
        return accumulativeCardRepository.addCard(card);
    }

    @Override
    @Transactional
    public AccumulativeCard deleteCard(AccumulativeCard card) {
        return accumulativeCardRepository.deleteCard(card);
    }

    @Override
    public Set<AccumulativeCard> findAll() {
        return accumulativeCardRepository.getCards();
    }
}
