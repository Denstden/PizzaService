package ua.com.rd.pizzaservice.repository.pizza.inmem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class InMemPizzaRepository implements PizzaRepository {
    @Autowired
    private List<Pizza> pizzaList;

    public InMemPizzaRepository() {
        pizzaList = new ArrayList<>();
    }

    @Override
    public Pizza addPizza(Pizza pizza) {
        pizzaList.add(pizza);
        return pizza;
    }

    @Override
    public Pizza getPizzaByID(Long id) {
        for (Pizza aPizzaList : pizzaList) {
            if (aPizzaList.getId().equals(id)) {
                return aPizzaList;
            }
        }
        return null;
    }

    @Override
    public Pizza delete(Pizza pizza) {
        pizzaList.remove(pizza);
        return pizza;
    }

    @Override
    public Set<Pizza> findAll() {
        return new HashSet<>(pizzaList);
    }
}
