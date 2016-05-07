package ua.com.rd.pizzaservice.repository.pizza.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Repository
public class PostgreSQLPizzaRepository implements PizzaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PostgreSQLPizzaRepository() {
    }

    public PostgreSQLPizzaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Pizza addPizza(Pizza pizza) {
        entityManager.persist(pizza);
        return pizza;
    }

    @Override
    public Pizza getPizzaByID(Long id) {
        return entityManager.find(Pizza.class, id);
    }

    @Override
    public Pizza delete(Pizza pizza) {
        Pizza managedPizza = entityManager.getReference(Pizza.class, pizza.getId());
        entityManager.remove(managedPizza);
        entityManager.flush();
        return managedPizza;
    }

    @Override
    public Set<Pizza> findAll() {
        return new HashSet<>(entityManager.createQuery("" +
                "SELECT p FROM Pizza p", Pizza.class).getResultList());
    }
}
