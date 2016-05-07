package ua.com.rd.pizzaservice.domain.discount.pizzadiscount;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.HashMap;
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

    public Map<Pizza, Integer> getPizzas() {
        return pizzas;
    }

    @Override
    public void setPizzas(Map<Pizza, Integer> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public Double calculate() {
        Integer countPizzas = countPizzas();
        if (countPizzas > n) {
            return mostExpensivePizzaPrice() * percents / 100;
        }
        return 0.;
    }

    private Integer countPizzas() {
        Integer res = 0;
        for (Integer count : pizzas.values()) {
            res += count;
        }
        return res;
    }

    private Double mostExpensivePizzaPrice() {
        Double maxPrice = Double.MIN_VALUE;
        for (Pizza pizza : pizzas.keySet()) {
            if (pizza.getPrice() > maxPrice) {
                maxPrice = pizza.getPrice();
            }
        }
        return maxPrice;
    }
}
