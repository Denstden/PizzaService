package ua.com.rd.pizzaservice.service.customer;

import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.IncorrectStateException;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void giveCard(Long id) {
        customerById(id).giveCard();
    }

    @Override
    public boolean payForOrder(Order order){
        try {
            order.progress();
        } catch (IncorrectStateException e) {
            e.printStackTrace();
        }
        System.out.println("You must pay "+order.getFinalPrice()+" for your order.");

        try {
            order.done();
        } catch (IncorrectStateException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Customer customerById(Long id){
        return customerRepository.getCustomerById(id);
    }
}
