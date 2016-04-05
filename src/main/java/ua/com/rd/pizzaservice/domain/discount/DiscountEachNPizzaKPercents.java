package ua.com.rd.pizzaservice.domain.discount;

import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;

public class DiscountEachNPizzaKPercents implements PizzaDiscount {
    private Integer n;
    private Double percents;
    private List<Pizza> pizzas = new ArrayList<>();

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

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public Double calculate(/*Order order*/) {
        Double finalDiscount = 0.;
        int i=1;
        for (Pizza pizza:pizzas){
            if (i%n==0){
                finalDiscount+=pizza.getPrice()*percents/100;
            }
            i++;
        }
        return finalDiscount;
    }
}
