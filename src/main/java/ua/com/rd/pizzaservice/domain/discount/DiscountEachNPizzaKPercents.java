package ua.com.rd.pizzaservice.domain.discount;

import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

public class DiscountEachNPizzaKPercents implements Discountable {
    private Integer n;
    private Double percents;
    private Order order;

    public DiscountEachNPizzaKPercents(Integer n, Double percents) {
        this.n = n;
        this.percents = percents;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Double getPercents() {
        return percents;
    }

    public void setPercents(Double percents) {
        this.percents = percents;
    }

    @Override
    public Double calculate() {
        Double finalDiscount = 0.;
        int i=1;
        for (Pizza pizza:order.getPizzaList()){
            if (i%n==0){
                finalDiscount+=pizza.getPrice()*percents/100;
            }
            i++;
        }
        return finalDiscount;
    }
}
