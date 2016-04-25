package ua.com.rd.pizzaservice.repository.customer.inmem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

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
    public void saveCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public Customer getCustomerById(Long id){
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
    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }
}
