package ua.com.rd.pizzaservice.repository.pizza.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public void addPizza(Pizza pizza) {
        entityManager.persist(pizza);
    }

    @Override
    public Pizza getPizzaByID(Long id) {
        return entityManager.find(Pizza.class, id);
    }

    @Override
    public void update(Pizza pizza){
        Pizza pizza1 = getPizzaByID(pizza.getId());
        pizza1.setName(pizza.getName());
        pizza1.setPrice(pizza.getPrice());
        pizza1.setType(pizza.getType());
    }
    @Override
    public void delete(Pizza pizza){
        entityManager.remove(pizza);
    }

    @Override
    public List<Pizza> findAll() {
        return entityManager.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
    }
}
