package ua.com.rd.pizzaservice.service.customer;

import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.domain.order.IncorrectStateException;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.repository.customer.CustomerRepository;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private AccumulativeCardService cardService;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccumulativeCardService cardService) {
        this.customerRepository = customerRepository;
        this.cardService = cardService;
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
            cardService.findCard(order.getCustomer()).addCash(order.getFinalPrice());
        } catch (IncorrectStateException e) {
            e.printStackTrace();
        } catch (NoAccumulativeCardException ignored) {
        }
        return true;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
