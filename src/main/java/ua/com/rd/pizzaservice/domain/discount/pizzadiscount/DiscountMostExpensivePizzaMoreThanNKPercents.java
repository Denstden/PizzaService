package ua.com.rd.pizzaservice.domain.discount.pizzadiscount;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountMostExpensivePizzaMoreThanNKPercents
        implements PizzaDiscount {
    private Integer n;
    private Double percents;
    private Map<Pizza, Integer> pizzas = new HashMap<>();

    public DiscountMostExpensivePizzaMoreThanNKPercents(Integer n,
                                                        Double percents) {
        this.n = n;
        this.percents = percents;
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
    public void setPizzas(Map<Pizza, Integer> pizzas) {
        this.pizzas = pizzas;
    }

    public  Map<Pizza, Integer> getPizzas() {
        return pizzas;
    }

    @Override
    public Double calculate() {
        if (pizzas.size() > n) {
            return mostExpensivePizzaPrice(pizzas) * percents / 100;
        }
        return 0.;
    }

    private Double mostExpensivePizzaPrice(Map<Pizza, Integer> map) {
        Double maxPrice = Double.MIN_VALUE;
        for (Pizza pizza: map.keySet()) {
            if (pizza.getPrice() > maxPrice) {
                maxPrice = pizza.getPrice();
            }
        }
        return maxPrice;
    }
}
