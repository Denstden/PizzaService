package ua.com.rd.pizzaservice.repository.customer.inmem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class InMemCustomerRepository implements CustomerRepository {
    @Autowired
    private List<Customer> customers;

    public InMemCustomerRepository() {
        customers = new ArrayList<>();
    }

    public InMemCustomerRepository(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer getCustomerById(Long id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        Customer customer1 = getCustomerById(customer.getId());
        customer1.setName(customer.getName());
        customer1.setAddresses(customer.getAddresses());
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        if (customers.remove(customer)) {
            return customer;
        }
        return null;
    }

    @Override
    public Set<Customer> findAll() {
        return new HashSet<>(customers);
    }
}
