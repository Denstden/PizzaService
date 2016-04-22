package ua.com.rd.pizzaservice.service.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.domain.discount.*;
import ua.com.rd.pizzaservice.domain.discount.pizzadiscount.DiscountEachNPizzaKPercents;
import ua.com.rd.pizzaservice.domain.discount.pizzadiscount.DiscountMostExpensivePizzaMoreThanNKPercents;
import ua.com.rd.pizzaservice.domain.discount.pizzadiscount.PizzaDiscount;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class DiscountProvider {
    @Autowired
    private AccumulativeCardService accumulativeCardService;
    private Set<PizzaDiscount> pizzaDiscounts = new HashSet<>();
    private Set<AccumulativeCardDiscount> cardDiscounts = new HashSet<>();

    public DiscountProvider() {
    }

    @PostConstruct
    public void init(){
        pizzaDiscounts.add(new DiscountMostExpensivePizzaMoreThanNKPercents(4, 50d));
        pizzaDiscounts.add(new DiscountEachNPizzaKPercents(3, 30d));
        cardDiscounts.add(new AccumulativeCardDiscount());
    }

    public void setAccumulativeCardService(AccumulativeCardService accumulativeCardService) {
        this.accumulativeCardService = accumulativeCardService;
    }

    public void addDiscount(Discountable discount){
        if (discount instanceof PizzaDiscount){
            pizzaDiscounts.add((PizzaDiscount) discount);
        }
        else{
            cardDiscounts.add((AccumulativeCardDiscount) discount);
        }
    }

    public void deleteDiscount(Discountable discount){
        if (discount instanceof PizzaDiscount){
            pizzaDiscounts.remove(discount);
        }
        else{
            cardDiscounts.remove(discount);
        }
    }

    public List<Discountable> getDiscountsForOrder(Order order){
        List<Discountable> discounts = new ArrayList<>();
        addPizzaDiscounts(order.getPizzas(), discounts);
        addCardDiscounts(order, discounts);
        return discounts;
    }

    private void addCardDiscounts(Order order, List<Discountable> discounts) {
        try {
            AccumulativeCard card = accumulativeCardService.findCard(order.getCustomer());
            for (AccumulativeCardDiscount discount:cardDiscounts){
                discount.setFinalOrderPrice(order.getFinalPrice());
                discount.setCard(card);
                discounts.add(discount);
            }
        } catch (NoAccumulativeCardException ignored) {
        }
    }

    private void addPizzaDiscounts(Map<Pizza, Integer> pizzas, List<Discountable> discounts) {
        if (pizzas.size()>0) {
            for (PizzaDiscount discount : pizzaDiscounts) {
                discount.setPizzas(pizzas);
                discounts.add(discount);
            }
        }
    }
}
