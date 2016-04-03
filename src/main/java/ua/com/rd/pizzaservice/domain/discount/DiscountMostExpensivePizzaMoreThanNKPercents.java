package ua.com.rd.pizzaservice.domain.discount;

import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.List;

public class DiscountMostExpensivePizzaMoreThanNKPercents implements Discountable {
    private Integer n;
    private Double percents;
    private Order order;

    public DiscountMostExpensivePizzaMoreThanNKPercents(Integer n, Double percents) {
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

    public int getN() {
        return n;
    }

    public void setN(int n) {
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
        if (order.getPizzaList().size()>n){
            return mostExpensivePizzaPrice(order.getPizzaList())*percents/100;
        }
        return 0.;
    }

    private Double mostExpensivePizzaPrice(List<Pizza> pizzas){
        Double maxPrice = Double.MIN_VALUE;
        for (Pizza pizza:pizzas){
            if (pizza.getPrice()>maxPrice){
                maxPrice = pizza.getPrice();
            }
        }
        return maxPrice;
    }
}
