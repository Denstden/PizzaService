package ua.com.rd.pizzaservice.service.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.repository.card.AccumulativeCardRepository;

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
    @Transactional(readOnly = true)
    public AccumulativeCard findCard(Customer customer)
            throws NoAccumulativeCardException {
        return accumulativeCardRepository.findCardByCustomer(customer);
    }

    @Override
    @Transactional
    public AccumulativeCard giveCard(Customer customer) {
        return accumulativeCardRepository.addCard(customer);
    }

    @Override
    @Transactional
    public void deleteCard(Customer customer) {
        accumulativeCardRepository.deleteCard(customer);
    }
}
