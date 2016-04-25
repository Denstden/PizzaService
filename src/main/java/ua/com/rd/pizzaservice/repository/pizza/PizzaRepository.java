package ua.com.rd.pizzaservice.repository.pizza;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.List;

public interface PizzaRepository {
    void addPizza(Pizza pizza);
    Pizza getPizzaByID(Long id);
    void update(Pizza pizza);
    void delete(Pizza pizza);
    List<Pizza> findAll();
}
