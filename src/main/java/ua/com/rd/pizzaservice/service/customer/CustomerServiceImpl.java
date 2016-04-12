package ua.com.rd.pizzaservice.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.domain.order.IncorrectStateException;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;

import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccumulativeCardService accumulativeCardService;

    public CustomerServiceImpl() {
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void setAccumulativeCardService(AccumulativeCardService accumulativeCardService) {
        this.accumulativeCardService = accumulativeCardService;
    }

    @Override
    public boolean payForOrder(Order order){
        try {
            order.progress();
        } catch (IncorrectStateException e) {
            e.printStackTrace();
        }
        System.out.println("You must pay "+order.getFinalPrice()
                +" for your order.");

        try {
            order.done();
            accumulativeCardService.findCard(order.getCustomer()).addCash(
                    order.getFinalPrice());
        } catch (IncorrectStateException e) {
            e.printStackTrace();
        } catch (NoAccumulativeCardException ignored) {
        }
        return true;
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
