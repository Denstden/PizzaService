package ua.com.rd.pizzaservice.domain.discount;

import org.junit.Before;
import org.junit.Test;
import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.Customer;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccumulativeCardDiscountTest {
    private final static Double EPSILON = 0.0000001;
    private AccumulativeCardDiscount discount;
    private AccumulativeCard card;

    @Before
    public void setUp(){
        discount = new AccumulativeCardDiscount();
        Customer customer = new Customer(1l, "Ivan", new Address("C", "c", "str", "b"));
        card = new AccumulativeCard(customer);
    }

    @Test
    public void calculateDiscountCustomerWithoutCardShouldBeZero(){
        assertEquals(0d, discount.calculate(), EPSILON);
    }

    @Test
    public void calculateDiscountTenPercentsOfCashOnCardLessThanThirtyPercentsOfPrice(){
        card.addCash(200.);
        discount.setCard(card);
        discount.setFinalOrderPrice(100.);
        assertEquals(20d, discount.calculate(), EPSILON);
    }

    @Test
    public void calculateDiscountTenPercentsOfCashOnCardMoreThanThirtyPercentsOfPrice(){
        card.addCash(400.);
        discount.setCard(card);
        discount.setFinalOrderPrice(100.);
        assertEquals(30d, discount.calculate(), EPSILON);
    }

}
