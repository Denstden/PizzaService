package ua.com.rd.pizzaservice.repository.card.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.exception.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.repository.card.AccumulativeCardRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PostgreSQLAccumulativeCardRepository implements AccumulativeCardRepository {
    @PersistenceContext
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
    public AccumulativeCard addCard(AccumulativeCard card) {
        Customer managedCustomer = entityManager.getReference(Customer.class, card.getCustomer().getId());
        card.setCustomer(managedCustomer);
        entityManager.persist(card);
        return card;
    }

    @Override
    public Set<AccumulativeCard> getCards() {
        return new HashSet<>(entityManager.createQuery(
                "SELECT c FROM AccumulativeCard c JOIN FETCH c.customer cc JOIN FETCH cc.addresses",
                AccumulativeCard.class).getResultList());
    }

    @Override
    public void updateCard(AccumulativeCard card) {
        AccumulativeCard managedCard = getCardById(card.getId());
        managedCard.setCash(card.getCash());
        entityManager.flush();
    }

    @Override
    public AccumulativeCard getCardById(Long id) {
        TypedQuery<AccumulativeCard> query = entityManager.createQuery(
                "SELECT c FROM AccumulativeCard c JOIN FETCH c.customer cc" +
                        " LEFT JOIN FETCH cc.addresses WHERE c.id= :id", AccumulativeCard.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public AccumulativeCard deleteCard(AccumulativeCard card) {
        AccumulativeCard managedCard = entityManager.find(AccumulativeCard.class, card.getId());
        entityManager.remove(managedCard);
        entityManager.flush();
        return card;
    }

    @Override
    public AccumulativeCard getCardByCustomer(Customer customer) throws NoAccumulativeCardException {
        Customer attachedCustomer = entityManager.getReference(Customer.class, customer.getId());
        TypedQuery<AccumulativeCard> query = entityManager.createQuery(
                "SELECT c FROM AccumulativeCard c JOIN FETCH c.customer cc " +
                        "LEFT JOIN FETCH cc.addresses WHERE c.customer= :customer", AccumulativeCard.class);
        query.setParameter("customer", attachedCustomer);
        List<AccumulativeCard> cards = query.getResultList();
        if (cards.size() == 0) {
            throw new NoAccumulativeCardException();
        }
        return cards.get(0);
    }
}
