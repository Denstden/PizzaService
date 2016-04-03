package ua.com.rd.pizzaservice.repository.pizza;

import ua.com.rd.pizzaservice.domain.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;

public class InMemPizzaRepository implements PizzaRepository {
    private List<Pizza> pizzaList;

    public InMemPizzaRepository() {
        pizzaList = new ArrayList<>();
        pizzaList.add(new Pizza(1l,"Margarita",180., Pizza.PizzaType.MEAT));
        pizzaList.add(new Pizza(2l,"Barbecue",120., Pizza.PizzaType.MEAT));
        pizzaList.add(new Pizza(3l,"Four seasons",130., Pizza.PizzaType.VEGETARIAN));
        pizzaList.add(new Pizza(4l,"Sea pizza",150., Pizza.PizzaType.SEA));
    }

    public List<Pizza> getPizzaList() {
        return pizzaList;
    }

    public void setPizzaList(List<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
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
}
