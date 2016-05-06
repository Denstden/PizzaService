package ua.com.rd.pizzaservice.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.order.state.StateConverter;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServiceTestUtils extends AbstractTransactionalJUnit4SpringContextTests {
    private JdbcTemplate jdbcTemplate;

    public ServiceTestUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Pizza createPizza(Long pizzaId, String pizzaName, Double price, Pizza.PizzaType pizzaType) {
        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);
        pizza.setName(pizzaName);
        pizza.setPrice(price);
        pizza.setType(pizzaType);
        jdbcTemplate.update(
                "INSERT INTO pizzas (pizza_id, pizza_name, price, pizza_type, version) " +
                        "VALUES (?, ?, ?, ?, ?);",
                pizzaId, pizzaName, price, String.valueOf(pizzaType), 0);
        return pizza;
    }

    public Address createAddress(Long id, String country, String city, String street, String building){
        Address address = new Address(country, city, street, building);
        address.setId(id);
        jdbcTemplate.update(
                "INSERT INTO addresses (address_id, country, city, street, building, version) " +
                        "VALUES (?, ?, ?, ?, ?, ?);",
                address.getId(), address.getCountry(), address.getCity(), address.getStreet(),
                address.getBuilding(), 0);
        return address;
    }

    public Customer createCustomer(Long id, String name, Address address){
        Customer customer = new Customer();
        customer.setName(name);
        Set<Address> addresses = new HashSet<Address>(){{
            add(address);
        }};
        customer.setAddresses(addresses);
        customer.setId(id);
        jdbcTemplate.update(
                "INSERT INTO customers (customer_id, customer_name, version) " +
                        "VALUES (?, ?, ?)",
                customer.getId(), customer.getName(), 0);
        jdbcTemplate.update(
                "INSERT INTO customers_addresses (customer_customer_id, addresses_address_id) VALUES " +
                        "(?, ?)", customer.getId(), address.getId());
        return customer;
    }

    public Order createOrder(Long id, Map<Pizza, Integer> pizzas, Customer customer, Address address, Date creationDate, Date doneDate) {
        Order order = new Order();
        order.setId(id);
        order.setPizzas(pizzas);
        order.setCustomer(customer);
        order.setCreationDate(creationDate);
        order.setDoneDate(doneDate);
        order.setAddress(address);
        jdbcTemplate.update(
                "INSERT INTO orders (order_id, address_address_id, customer_customer_id, " +
                        "creation_date, done_date, currentstate, final_price, version) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                id, address.getId(), customer.getId(), creationDate, doneDate,
                new StateConverter().convertToDatabaseColumn(order.getCurrentState()), order.getFinalPrice(), 0);
        for (Map.Entry<Pizza, Integer> entry: pizzas.entrySet()){
            jdbcTemplate.update(
                "INSERT INTO orders_pizzas (order_order_id, pizza_id, count) VALUES (?, ?, ?)",
                    order.getId(), entry.getKey().getId(), entry.getValue());
        }
        return order;
    }

    public AccumulativeCard createCard(Customer customer, Long id){
        AccumulativeCard card = new AccumulativeCard();
        card.setId(id);
        card.setCash(0D);
        card.setCustomer(customer);
        jdbcTemplate.update(
                "INSERT INTO accumulative_cards (accumulative_card_id, customer_customer_id, cash, version) VALUES (?, ?, ?, ?)",
                card.getId(), customer.getId(), card.getCash(), 0);
        return card;
    }
}
