package ua.com.rd.pizzaservice.domain.discount;

import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;

public class DiscountMostExpensivePizzaMoreThanNKPercents implements PizzaDiscount {
    private Integer n;
    private Double percents;
    private List<Pizza> pizzas = new ArrayList<>();

    public DiscountMostExpensivePizzaMoreThanNKPercents(Integer n, Double percents) {
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
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public Double calculate(/*Order order*/) {
        if (pizzas.size()>n){
            return mostExpensivePizzaPrice(pizzas)*percents/100;
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
