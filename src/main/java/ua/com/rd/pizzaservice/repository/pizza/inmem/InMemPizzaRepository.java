package ua.com.rd.pizzaservice.repository.pizza.inmem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemPizzaRepository implements PizzaRepository {
    @Autowired
    private List<Pizza> pizzaList;

    public InMemPizzaRepository() {
        pizzaList = new ArrayList<>();
    }

    @Override
    public void addPizza(Pizza pizza) {
        pizzaList.add(pizza);
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
    public void update(Pizza pizza) {
        Pizza pizza1 = getPizzaByID(pizza.getId());
        pizzaList.remove(pizza1);
        pizzaList.add(pizza);
    }

    @Override
    public void delete(Pizza pizza) {
        pizzaList.remove(pizza);
    }
}
