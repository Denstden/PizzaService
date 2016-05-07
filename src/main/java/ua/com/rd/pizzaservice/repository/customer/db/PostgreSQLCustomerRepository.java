package ua.com.rd.pizzaservice.repository.customer.db;

import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Customer saveCustomer(Customer customer) {
        Set<Address> managedAddresses = new HashSet<>();
        for (Address address : customer.getAddresses()) {
            managedAddresses.add(entityManager.merge(address));
        }
        customer.setAddresses(managedAddresses);
        entityManager.persist(customer);
        return customer;
    }

    @Override
    public Customer getCustomerById(Long id) {
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c LEFT JOIN FETCH c.addresses WHERE c.id= :id", Customer.class);
        query.setParameter("id", id);
        List<Customer> customers = query.getResultList();
        if (customers.size() == 0) {
            return null;
        }
        return customers.get(0);
    }

    @Override
    public void updateCustomer(Customer customer) {
        Customer managedCustomer = entityManager.find(Customer.class, customer.getId());
        managedCustomer.setName(customer.getName());
        Set<Address> addresses = new HashSet<>();
        for (Address address : customer.getAddresses()) {
            addresses.add(entityManager.merge(address));
        }
        managedCustomer.setAddresses(addresses);
        entityManager.flush();
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer managedCustomer = entityManager.find(Customer.class, customer.getId());
        entityManager.remove(managedCustomer);
        entityManager.flush();
        return customer;
    }

    @Override
    public Set<Customer> findAll() {
        return new HashSet<>(entityManager.createQuery(
                "SELECT c FROM Customer c LEFT JOIN FETCH c.addresses", Customer.class).getResultList());
    }
}
