package ua.com.rd.pizzaservice.repository.order.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class PostgreSQLOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PostgreSQLOrderRepository() {
    }

    public PostgreSQLOrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long saveOrder(Order order) {
        Customer customer = entityManager.merge(order.getCustomer());
        order.setCustomer(customer);
        entityManager.persist(order);
        return order.getId();
    }

    @Override
    public Order getOrderById(Long id) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o JOIN FETCH o.customer, o.address WHERE o.id= :id", Order.class);
        query.setParameter("id", id);
        List<Order> orders = query.getResultList();
        if (orders.size()==0){
            return null;
        }
        return orders.get(0);
    }

    @Override
    public void updateOrder(Order order) {
        Order order1 = getOrderById(order.getId());
        order1.setPizzas(order.getPizzas());
        order1.setCustomer(order.getCustomer());
        order1.setCreationDate(order.getCreationDate());
        order1.setDoneDate(order.getDoneDate());
        order1.setCurrentState(order.getCurrentState());
        order1.setFinalPrice(order.getFinalPrice());
    }

    @Override
    public void deleteOrder(Order order) {
        entityManager.remove(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return entityManager.createQuery("SELECT o FROM Order o JOIN FETCH o.customer, o.address", Order.class).getResultList();
    }

    @Override
    public Long countOfOrders() {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Order.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
