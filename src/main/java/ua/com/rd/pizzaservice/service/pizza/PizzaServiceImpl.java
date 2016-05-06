package ua.com.rd.pizzaservice.service.pizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.repository.pizza.PizzaRepository;

import java.util.Set;

@Service
public class PizzaServiceImpl implements PizzaService{
    @Autowired
    private PizzaRepository pizzaRepository;

    public PizzaServiceImpl() {
    }

    public PizzaRepository getPizzaRepository() {
        return pizzaRepository;
    }

    public void setPizzaRepository(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }


    @Override
    @Transactional
    public Pizza savePizza(Pizza pizza) {
        return pizzaRepository.addPizza(pizza);
    }

    @Override
    @Transactional(readOnly = true)
    public Pizza getPizzaById(Long id) {
        return  pizzaRepository.getPizzaByID(id);
    }

    @Override
    public Pizza deletePizza(Pizza pizza) {
        pizzaRepository.delete(pizza);
        return pizza;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Pizza> findAll() {
        return pizzaRepository.findAll();
    }
}
