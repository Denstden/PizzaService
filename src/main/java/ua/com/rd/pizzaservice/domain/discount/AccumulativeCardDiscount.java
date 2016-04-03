package ua.com.rd.pizzaservice.domain.discount;

import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.domain.order.Order;

public class AccumulativeCardDiscount implements Discountable{
    private static final Double PERCENT_FOR_SUBSTRACTION_FROM_CARD = 10d;
    private static final Double MAX_PERCENT_FOR_SUBSTRACTION = 30d;
    private Order order;

    public AccumulativeCardDiscount() {
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Double calculate() {
        Double cashOnCard;
        try {
            cashOnCard = order.getCustomer().getCashOnCard();
        } catch (NoAccumulativeCardException e) {
            return 0d;
        }
        Double discount;
        Double finalOrderPrice = order.getFinalPrice();
        if (cashOnCard * PERCENT_FOR_SUBSTRACTION_FROM_CARD > finalOrderPrice * MAX_PERCENT_FOR_SUBSTRACTION) {
            discount = finalOrderPrice * MAX_PERCENT_FOR_SUBSTRACTION / 100;
        } else {
            discount = cashOnCard * PERCENT_FOR_SUBSTRACTION_FROM_CARD / 100;
        }
        return discount;
    }
}
