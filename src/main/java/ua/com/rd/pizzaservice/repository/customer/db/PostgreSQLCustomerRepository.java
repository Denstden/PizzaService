package ua.com.rd.pizzaservice.repository.customer.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PostgreSQLCustomerRepository implements CustomerRepository {
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
        return entityManager.find(Customer.class, id);
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
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }
}
