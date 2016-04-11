package ua.com.rd.pizzaservice.repository.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemCustomerRepository implements CustomerRepository{
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
    public Customer getCustomerById(Long id){
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }
}
