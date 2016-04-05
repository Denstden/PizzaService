package ua.com.rd.pizzaservice.repository.customer;

import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class InMemCustomerRepository implements CustomerRepository{
    private List<Customer> customers;

    public InMemCustomerRepository() {
        customers = new ArrayList<Customer>(){{
            add(new Customer(1l, "Customer 1", new Address("Ukraine", "Kiev", "Peremogy","12b")));
            add(new Customer(2l, "Customer 2", new Address("Ukraine", "Kiev", "Sechenova","6a")));
            add(new Customer(3l, "Customer 3", new Address("Ukraine", "Kiev", "Kudriashova","14")));
            add(new Customer(4l, "Customer 4", new Address("Ukraine", "Kiev", "Kudriashova","18")));
        }};
    }

    public InMemCustomerRepository(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
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
