package ua.com.rd.pizzaservice.domain.discount;

import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;

public class AccumulativeCardDiscount implements Discountable {
    private static final Double PERCENT_FOR_SUBSTRACTION_FROM_CARD = 10d;
    private static final Double MAX_PERCENT_FOR_SUBSTRACTION = 30d;
    private Double finalOrderPrice;
    private AccumulativeCard card;

    public AccumulativeCardDiscount() {
    }

    public AccumulativeCard getCard() {
        return card;
    }

    public void setCard(AccumulativeCard card) {
        this.card = card;
    }

    public Double getFinalOrderPrice() {
        return finalOrderPrice;
    }

    public void setFinalOrderPrice(Double finalOrderPrice) {
        this.finalOrderPrice = finalOrderPrice;
    }

    @Override
    public Double calculate() {
        if (card == null) {
            return 0d;
        }
        Double cashOnCard = card.getCash();
        Double discount;
        if (cashOnCard * PERCENT_FOR_SUBSTRACTION_FROM_CARD
                > finalOrderPrice * MAX_PERCENT_FOR_SUBSTRACTION) {
            discount = finalOrderPrice * MAX_PERCENT_FOR_SUBSTRACTION / 100;
        } else {
            discount = cashOnCard * PERCENT_FOR_SUBSTRACTION_FROM_CARD / 100;
        }
        return discount;
    }
}
