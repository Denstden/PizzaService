package ua.com.rd.pizzaservice.service.card;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.exception.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.service.ServiceTestUtils;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/inMemDbRepositoryContext.xml",
        "classpath:/appContext.xml",
})
public class AccumulativeCardServiceImplInMemDbTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private AccumulativeCardService accumulativeCardService;

    private ServiceTestUtils serviceTestUtils;

    @Before
    public void setUp() {
        serviceTestUtils = new ServiceTestUtils(jdbcTemplate);
    }

    @Test
    public void createCardTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);
        AccumulativeCard card = new AccumulativeCard(customer);
        accumulativeCardService.createCard(card);
        Assert.assertNotNull(card.getId());
    }

    @Test(expected = NoAccumulativeCardException.class)
    public void findCardTestShouldRiseException() throws NoAccumulativeCardException {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);
        customer.setId(2L);
        accumulativeCardService.findCard(customer);
    }

    @Test
    public void findCardTest() throws NoAccumulativeCardException {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);
        AccumulativeCard card1 = serviceTestUtils.createCard(customer, 1L);
        AccumulativeCard card = accumulativeCardService.findCard(customer);
        Assert.assertEquals(card1, card);
    }

    @Test
    public void deleteCardTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);
        AccumulativeCard card = serviceTestUtils.createCard(customer, 1L);
        accumulativeCardService.deleteCard(card);

        int countOfRecords = getCountOfCardsById(card.getId());
        Assert.assertEquals(0, countOfRecords);
    }

    private int getCountOfCardsById(Long cardId) {
        final String sql = "SELECT COUNT(*) FROM accumulative_cards WHERE accumulative_card_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{cardId}, Integer.class);
    }

    @Test
    public void findAllTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);
        AccumulativeCard card1 = serviceTestUtils.createCard(customer, 1L);
        AccumulativeCard card2 = serviceTestUtils.createCard(customer, 2L);
        AccumulativeCard card3 = serviceTestUtils.createCard(customer, 3L);

        Set<AccumulativeCard> cards = accumulativeCardService.findAll();
        Assert.assertEquals(3, cards.size());

        Set<AccumulativeCard> expected = new HashSet<>();
        expected.add(card1);
        expected.add(card2);
        expected.add(card3);
        Assert.assertEquals(expected, cards);
    }

    @Test
    public void addCashToCardTest() {
        Address address = serviceTestUtils.createAddress(1L, "Country", "City", "Street", "Building");
        Customer customer = serviceTestUtils.createCustomer(1L, "Petya", address);
        AccumulativeCard card = serviceTestUtils.createCard(customer, 1L);

        final double cashToAdding = 150.;
        accumulativeCardService.addCashToCard(card, cashToAdding);

        final String sql = "SELECT cash FROM accumulative_cards WHERE accumulative_card_id = ?";
        double cash = jdbcTemplate.queryForObject(sql, new Object[]{card.getId()}, Double.class);
        Assert.assertEquals(cashToAdding, cash, 0.0001);
    }
}
