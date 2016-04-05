package ua.com.rd.pizzaservice.domain.discount;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.List;

public interface PizzaDiscount extends Discountable {
    void setPizzas(List<Pizza> pizzas);
}
