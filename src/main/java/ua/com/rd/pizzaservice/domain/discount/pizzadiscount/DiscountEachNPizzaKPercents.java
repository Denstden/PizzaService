package ua.com.rd.pizzaservice.domain.discount.pizzadiscount;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountEachNPizzaKPercents implements PizzaDiscount {
    private Integer n;
    private Double percents;
    private Map<Pizza, Integer> pizzas = new HashMap<>();

    public DiscountEachNPizzaKPercents(Integer n, Double percents) {
        this.n = n;
        this.percents = percents;
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

    public Map<Pizza, Integer> getPizzas() {
        return pizzas;
    }

    @Override
    public void setPizzas(Map<Pizza, Integer> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public Double calculate() {
        Double finalDiscount = 0.;
        int i = 1;
        for (Map.Entry<Pizza, Integer> entry: pizzas.entrySet()){
            for (int j=0;j<entry.getValue();j++){
                if (i % n == 0) {
                    finalDiscount += entry.getKey().getPrice() * percents / 100;
                }
                i++;
            }
            if (i % n == 0) {
                finalDiscount += entry.getKey().getPrice() * percents / 100;
            }
            i++;
        }
        return finalDiscount;
    }
}
