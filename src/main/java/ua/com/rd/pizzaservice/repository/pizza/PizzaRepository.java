package ua.com.rd.pizzaservice.repository.pizza;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

public interface PizzaRepository {
    void addPizza(Pizza pizza);
    Pizza getPizzaByID(Long id);
}
