package ua.com.rd.pizzaservice.domain.customer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccumulativeCardTest {
    private AccumulativeCard card;

    @Before
    public void setUp(){
        card = new AccumulativeCard();
    }

    @Test
    public void addCashTest(){
        card.addCash(150d);
        Double expected = 150d;
        assertEquals(expected, card.getCash());
    }
}
