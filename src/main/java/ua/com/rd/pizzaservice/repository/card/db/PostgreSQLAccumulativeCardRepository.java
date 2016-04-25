package ua.com.rd.pizzaservice.repository.card.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.repository.card.AccumulativeCardRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PostgreSQLAccumulativeCardRepository implements AccumulativeCardRepository{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public PostgreSQLAccumulativeCardRepository() {
    }

    public PostgreSQLAccumulativeCardRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AccumulativeCard addCard(Customer customer) {
        AccumulativeCard card = new AccumulativeCard(customer);
        entityManager.persist(card);
        return card;
    }

    @Override
    public Set<AccumulativeCard> getCards() {
        return new HashSet<>(entityManager.createQuery("SELECT c FROM AccumulativeCard c", AccumulativeCard.class).getResultList());
    }

    @Override
    public void updateCard(AccumulativeCard card) {
        AccumulativeCard card1 = getCardById(card.getId());
        card1.setCash(card.getCash());
        card1.setCustomer(card.getCustomer());
    }

    @Override
    public AccumulativeCard getCardById(Long id) {
        return entityManager.find(AccumulativeCard.class, id);
    }

    @Override
    public AccumulativeCard deleteCard(Customer customer) {
        try {
            AccumulativeCard card = findCardByCustomer(customer);
            entityManager.remove(card);
            return card;
        } catch (NoAccumulativeCardException e) {
            return null;
        }
    }

    @Override
    public AccumulativeCard findCardByCustomer(Customer customer) throws NoAccumulativeCardException {
        List<AccumulativeCard> cards = entityManager.createQuery("SELECT c FROM AccumulativeCard c WHERE c.customer=?1", AccumulativeCard.class)
                .setParameter(1, customer.getId()).getResultList();
        if (cards.size()==0){
            throw  new NoAccumulativeCardException();
        }
        return cards.get(0);
    }
}
