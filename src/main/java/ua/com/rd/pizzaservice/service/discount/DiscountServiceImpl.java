package ua.com.rd.pizzaservice.service.discount;

import ua.com.rd.pizzaservice.domain.discount.AccumulativeCardDiscount;
import ua.com.rd.pizzaservice.domain.discount.DiscountEachNPizzaKPercents;
import ua.com.rd.pizzaservice.domain.discount.DiscountMostExpensivePizzaMoreThanNKPercents;
import ua.com.rd.pizzaservice.domain.discount.Discountable;
import ua.com.rd.pizzaservice.domain.order.Order;

import java.util.HashSet;
import java.util.Set;

public class DiscountServiceImpl implements DiscountService {
    private Set<Discountable> discounts = new HashSet<>();

    {
        discounts.add(new DiscountMostExpensivePizzaMoreThanNKPercents(4, 50d));
        discounts.add(new DiscountEachNPizzaKPercents(3, 30d));
        discounts.add(new AccumulativeCardDiscount());
    }

    @Override
    public Double calculateDiscounts(Order order){
        Double discount = 0d;
        constructDiscounts(order);
        for (Discountable discountable :discounts){
            discount += discountable.calculate();
        }
        order.setFinalPrice(order.getFinalPrice()-discount);
        return discount;
    }

    private void constructDiscounts(Order order){
        for (Discountable discountable:discounts){
            discountable.setOrder(order);
        }
    }
}
