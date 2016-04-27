package ua.com.rd.pizzaservice.repository.customer.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PostgreSQLCustomerRepository implements CustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PostgreSQLCustomerRepository() {
    }

    public PostgreSQLCustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c JOIN FETCH c.addresses WHERE c.id= :id", Customer.class);
        query.setParameter("id", id);
        List<Customer> customers = query.getResultList();
        if (customers.size()==0){
            return null;
        }
        return customers.get(0);
    }

    @Override
    public void updateCustomer(Customer customer) {
        Customer customer1 = getCustomerById(customer.getId());
        customer1.setName(customer.getName());
        customer1.setAddresses(customer.getAddresses());
    }

    @Override
    public void deleteCustomer(Customer customer) {
        entityManager.remove(customer);
    }

    @Override
    public List<Customer> findAll() {
        return entityManager.createQuery(
                "SELECT c FROM Customer c JOIN FETCH c.addresses", Customer.class).getResultList();
    }
}
