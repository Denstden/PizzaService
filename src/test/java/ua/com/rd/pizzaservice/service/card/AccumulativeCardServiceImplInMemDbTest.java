package ua.com.rd.pizzaservice.service.card;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/inMemDbRepositoryContext.xml",
        "classpath:/appContext.xml",
})
public class AccumulativeCardServiceImplInMemDbTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private AccumulativeCardService accumulativeCardService;

    @Test
    public void createCardTest(){
        Address address = createAddress("Country", "City", "Street", "Building");
        Customer customer = createCustomer("Petya", address);
        AccumulativeCard card = new AccumulativeCard(customer);
        accumulativeCardService.createCard(card);
        Assert.assertNotNull(card.getId());
    }

    private Customer createCustomer(String name, Address address){
        Customer customer = new Customer();
        customer.setName(name);
        Set<Address> addresses = new HashSet<Address>(){{
            add(address);
        }};
        customer.setAddresses(addresses);
        customer.setId(1L);
        jdbcTemplate.update(
                "INSERT INTO customers (customer_id, customer_name) VALUES (?, ?)", customer.getId(), customer.getName());
        jdbcTemplate.update(
                "INSERT INTO customers_addresses (customer_customer_id, addresses_address_id) VALUES " +
                        "(?, ?)", customer.getId(), address.getId());
        return customer;
    }

    private Address createAddress(String country, String city, String street, String building){
        Address address = new Address(country, city, street, building);
        address.setId(1L);
        jdbcTemplate.update(
                "INSERT INTO addresses (address_id, country, city, street, building) VALUES (?, ?, ?, ?, ?);",
                address.getId(), address.getCountry(), address.getCity(), address.getStreet(), address.getBuilding());
        return address;
    }

    @Test(expected = NoAccumulativeCardException.class)
    public void findCardTestShouldRiseException() throws NoAccumulativeCardException {
        Address address = createAddress("Country", "City", "Street", "Building");
        Customer customer = createCustomer("Petya", address);
        customer.setId(2L);
        accumulativeCardService.findCard(customer);
    }

    @Test
    public void findCardTest() throws NoAccumulativeCardException {
        Address address = createAddress("Country", "City", "Street", "Building");
        Customer customer = createCustomer("Petya", address);
        Long id = createCard(customer);
        AccumulativeCard card = accumulativeCardService.findCard(customer);
        Assert.assertEquals(id, card.getId());
    }

    private Long createCard(Customer customer){
        final String sql = "INSERT INTO accumulative_cards (cash, customer_customer_id) VALUES (0, "+customer.getId()+")";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    }
                }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Test
    public void deleteCardTest() {
        Address address = createAddress("Country", "City", "Street", "Building");
        Customer customer = createCustomer("Petya", address);
        AccumulativeCard card = createNativeCard(customer);
        accumulativeCardService.deleteCard(card);

        List<AccumulativeCard> cardList = jdbcTemplate.query(
                "SELECT cash, customer_customer_id from accumulative_cards where accumulative_card_id = " + card.getId(),
                (resultSet, i) -> {
                    AccumulativeCard card2 = new AccumulativeCard();
                    card2.setId(card.getId());
                    card2.setCustomer(null);
                    card2.setCash(resultSet.getDouble("cash"));
                    return card2;
                });
        Assert.assertEquals(0, cardList.size());
    }

    private AccumulativeCard createNativeCard(Customer customer){
        AccumulativeCard card = new AccumulativeCard();
        card.setId(1L);
        card.setCash(0D);
        card.setCustomer(customer);
        jdbcTemplate.update(
                "INSERT INTO accumulative_cards (accumulative_card_id, customer_customer_id, cash) VALUES (?, ?, ?)",
                card.getId(), customer.getId(), card.getCash());
        return card;
    }
}
