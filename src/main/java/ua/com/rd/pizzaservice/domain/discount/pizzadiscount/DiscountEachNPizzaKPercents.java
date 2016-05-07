package ua.com.rd.pizzaservice.domain.discount.pizzadiscount;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.*;

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
        List<Double> prices = getPrices();
        Collections.sort(prices);
        for (int i = 0; i < prices.size() / n; i++) {
            finalDiscount += prices.get(prices.size() - i - 1) * percents / 100;
        }
        return finalDiscount;
    }

    private List<Double> getPrices() {
        List<Double> result = new ArrayList<>();
        for (Map.Entry<Pizza, Integer> entry : pizzas.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey().getPrice());
            }
        }
        return result;
    }
}
