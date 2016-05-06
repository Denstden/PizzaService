package ua.com.rd.pizzaservice.repository.pizza;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.Set;

public interface PizzaRepository {
    Pizza addPizza(Pizza pizza);
    Pizza getPizzaByID(Long id);
    Pizza delete(Pizza pizza);
    Set<Pizza> findAll();
}
