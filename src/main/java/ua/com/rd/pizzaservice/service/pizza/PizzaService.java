package ua.com.rd.pizzaservice.service.pizza;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.List;

public interface PizzaService {
    void savePizza(Pizza pizza);
    Pizza getPizzaById(Long id);
    List<Pizza> findAll();
}
