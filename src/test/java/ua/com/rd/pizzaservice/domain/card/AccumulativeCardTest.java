package ua.com.rd.pizzaservice.domain.card;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.customer.Customer;

import static org.junit.Assert.assertEquals;

public class AccumulativeCardTest {
    private AccumulativeCard card;

    @Before
    public void setUp() {
        Customer customer = new Customer(1L, "Customer1", new Address("Country", "City", "Street", "building"));
        card = new AccumulativeCard(customer);
    }

    @Test
    public void addCashTest() {
        card.addCash(150d);
        Double expected = 150d;
        assertEquals(expected, card.getCash());
    }
}
