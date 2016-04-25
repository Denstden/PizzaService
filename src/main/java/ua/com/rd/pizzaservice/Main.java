package ua.com.rd.pizzaservice;

import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.IncorrectStateException;
import ua.com.rd.pizzaservice.domain.order.state.InProgressState;
import ua.com.rd.pizzaservice.domain.order.state.State;
import ua.com.rd.pizzaservice.domain.order.state.StateConverter;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.customer.db.PostgreSQLCustomerRepository;
import ua.com.rd.pizzaservice.repository.pizza.db.PostgreSQLPizzaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IncorrectStateException {
        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();

        Pizza pizza = new Pizza();
        pizza.setName("Pizza 1");
        pizza.setPrice(100.0);
        pizza.setType(Pizza.PizzaType.MEAT);

        try{
           *//* em.getTransaction().begin();;
			em.persist(pizza);
            em.getTransaction().commit();*//*

            Pizza p = em.find(Pizza.class, 6L);
            System.out.println(p);
        }
        finally{
            em.close();
            emf.close();
        }*/
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("jpa");
            em = emf.createEntityManager();

       /* PostgreSQLPizzaRepository pizzaRepository = new PostgreSQLPizzaRepository(em);
        em.getTransaction().begin();
        System.out.println( pizzaRepository.findAll());
        em.getTransaction().commit();*/

            PostgreSQLCustomerRepository customerRepository = new PostgreSQLCustomerRepository(em);
            em.getTransaction().begin();
            /*Customer customer = new Customer();
            customer.setName("Customer2");
            Address address = new Address("Ukraine", "Kiev", "Khreshchatik", "1");
            Set<Customer> customers = new HashSet<Customer>() {{
                add(customer);
            }};
            address.setCustomer(customers);
            Set<Address> addresses = new HashSet<Address>() {{
                add(address);
            }};
            customer.setAddresses(addresses);
            customerRepository.saveCustomer(customer);*/
            System.out.println(customerRepository.findAll());

            em.getTransaction().commit();

        }
        finally {
            em.close();
            emf.close();
        }

    }
}
