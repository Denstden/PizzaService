package ua.com.rd.pizzaservice.service.discount;

import ua.com.rd.pizzaservice.domain.card.AccumulativeCard;
import ua.com.rd.pizzaservice.domain.customer.NoAccumulativeCardException;
import ua.com.rd.pizzaservice.domain.discount.*;
import ua.com.rd.pizzaservice.domain.discount.pizzadiscount.DiscountEachNPizzaKPercents;
import ua.com.rd.pizzaservice.domain.discount.pizzadiscount.DiscountMostExpensivePizzaMoreThanNKPercents;
import ua.com.rd.pizzaservice.domain.discount.pizzadiscount.PizzaDiscount;
import ua.com.rd.pizzaservice.domain.order.Order;
import ua.com.rd.pizzaservice.domain.pizza.Pizza;
import ua.com.rd.pizzaservice.service.card.AccumulativeCardService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiscountProvider {
    private AccumulativeCardService service;
    private Set<PizzaDiscount> pizzaDiscounts = new HashSet<>();
    private Set<AccumulativeCardDiscount> cardDiscounts = new HashSet<>();

    {
        pizzaDiscounts.add(new DiscountMostExpensivePizzaMoreThanNKPercents(4, 50d));
        pizzaDiscounts.add(new DiscountEachNPizzaKPercents(3, 30d));
        cardDiscounts.add(new AccumulativeCardDiscount());
    }

    public DiscountProvider(AccumulativeCardService service) {
        this.service = service;
    }

    public AccumulativeCardService getService() {
        return service;
    }

    public void setService(AccumulativeCardService service) {
        this.service = service;
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
        addPizzaDiscounts(order.getPizzaList(), discounts);
        addCardDiscounts(order, discounts);
        return discounts;
    }

    private void addCardDiscounts(Order order, List<Discountable> discounts) {
        try {
            AccumulativeCard card = service.findCard(order.getCustomer());
            for (AccumulativeCardDiscount discount:cardDiscounts){
                discount.setFinalOrderPrice(order.getFinalPrice());
                discount.setCard(card);
                discounts.add(discount);
            }
        } catch (NoAccumulativeCardException ignored) {
        }
    }

    private void addPizzaDiscounts(List<Pizza> pizzas, List<Discountable> discounts) {
        if (pizzas.size()>0) {
            for (PizzaDiscount discount : pizzaDiscounts) {
                discount.setPizzas(pizzas);
                discounts.add(discount);
            }
        }
    }
}
