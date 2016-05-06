package ua.com.rd.pizzaservice.repository.order.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.order.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.*;

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
        Address managedOrdersAddress = entityManager.merge(order.getAddress());
        order.setAddress(managedOrdersAddress);
        Set<Address> managedCustomersAddresses = new HashSet<>();
        for (Address address: order.getCustomer().getAddresses()){
            managedCustomersAddresses.add(entityManager.merge(address));
        }
        order.getCustomer().setAddresses(managedCustomersAddresses);
        Customer managedCustomer = entityManager.merge(order.getCustomer());
        order.setCustomer(managedCustomer);
        entityManager.persist(order);
        return order.getId();
    }

    @Override
    public Order getOrderById(Long id) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o JOIN FETCH o.pizzas WHERE o.id= :id", Order.class);
        query.setParameter("id", id);
        List<Order> orders = query.getResultList();
        if (orders.size()==0){
            return null;
        }
        return orders.get(0);
    }

    @Override
    public void updateOrder(Order order) {
        Order managedOrder = entityManager.find(Order.class, order.getId());
        Map<Pizza, Integer> managedPizzas = new HashMap<>();
        System.out.println(order.getPizzas());
        for (Map.Entry<Pizza, Integer> pizzas : order.getPizzas().entrySet()){
            managedPizzas.put(entityManager.find(Pizza.class, pizzas.getKey().getId()), pizzas.getValue());
        }
        managedOrder.setAddress(entityManager.find(Address.class, order.getAddress().getId()));
        managedOrder.setPizzas(managedPizzas);
        managedOrder.setCustomer(entityManager.find(Customer.class, order.getCustomer().getId()));
        managedOrder.setCreationDate(order.getCreationDate());
        managedOrder.setDoneDate(order.getDoneDate());
        managedOrder.setCurrentState(order.getCurrentState());
        managedOrder.setFinalPrice(order.getFinalPrice());
        entityManager.merge(managedOrder);
        entityManager.flush();
    }

    @Override
    public void deleteOrder(Order order) {
        Order managedOrder = entityManager.find(Order.class, order.getId());
        entityManager.remove(managedOrder);
        entityManager.flush();
    }

    @Override
    public Set<Order> getAllOrders() {
        return new HashSet<>(entityManager.createQuery("" +
                "SELECT o FROM Order o JOIN FETCH o.pizzas",
                Order.class).getResultList());
    }

    @Override
    public Long countOfOrders() {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Order.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
