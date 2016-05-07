package ua.com.rd.pizzaservice.repository.customer;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.repository.customer.inmem.InMemCustomerRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InMemCustomerRepositoryTest {
    private InMemCustomerRepository repository;

    @Before
    public void setUp() {
        repository = new InMemCustomerRepository();
    }

    @Test
    public void getCustomerByIdShouldExists() {
        Customer customer = new Customer(10L, "name", new Address("C", "c", "st", "b"));
        repository.getCustomers().add(customer);
        assertEquals(customer, repository.getCustomerById(10L));
    }

    @Test
    public void getCustomerByIdShouldNotExists() {
        Customer customer = new Customer(10L, "name", new Address("C", "c", "st", "b"));
        repository.getCustomers().add(customer);
        assertNull(repository.getCustomerById(13L));
    }

}
