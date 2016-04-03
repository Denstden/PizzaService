package ua.com.rd.pizzaservice.repository.pizza;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

public interface PizzaRepository {
    Pizza getPizzaByID(Long id);
}
