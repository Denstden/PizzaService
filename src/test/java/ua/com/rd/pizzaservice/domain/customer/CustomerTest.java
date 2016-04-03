package ua.com.rd.pizzaservice.domain.customer;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private final static Double EPSILON = 0.0000001;
    private Customer customer;

    @Before
    public void setUp(){
        customer = new Customer(1l,"Name",new Address("Country","City","Street","Building"));
    }

    @Test
    public void isCardShouldBeFalse(){
        assertEquals(false, customer.isCardPresent());
    }

    @Test
    public void isCardShouldBeTrue(){
        customer.giveCard();
        assertEquals(true, customer.isCardPresent());
    }

    @Test
    public void activateCardTwoTimes() throws NoAccumulativeCardException {
        customer.giveCard();
        customer.addCashToCard(15d);
        customer.giveCard();
        assertEquals(15d, customer.getCashOnCard(), EPSILON);
    }
}
