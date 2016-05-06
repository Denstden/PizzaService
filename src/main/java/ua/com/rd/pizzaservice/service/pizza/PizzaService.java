package ua.com.rd.pizzaservice.service.pizza;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.Set;

public interface PizzaService {
    Pizza savePizza(Pizza pizza);
    Pizza getPizzaById(Long id);
    Pizza deletePizza(Pizza pizza);
    Set<Pizza> findAll();
}
