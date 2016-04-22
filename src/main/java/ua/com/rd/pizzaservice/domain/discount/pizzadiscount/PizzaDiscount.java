package ua.com.rd.pizzaservice.domain.discount.pizzadiscount;

import ua.com.rd.pizzaservice.domain.discount.Discountable;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.List;
import java.util.Map;

public interface PizzaDiscount extends Discountable {
    void setPizzas(Map<Pizza, Integer> pizzas);
}
