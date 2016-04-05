package ua.com.rd.pizzaservice.service.discount;

import ua.com.rd.pizzaservice.domain.discount.AccumulativeCardDiscount;
import ua.com.rd.pizzaservice.domain.discount.DiscountEachNPizzaKPercents;
import ua.com.rd.pizzaservice.domain.discount.DiscountMostExpensivePizzaMoreThanNKPercents;
import ua.com.rd.pizzaservice.domain.discount.Discountable;
import ua.com.rd.pizzaservice.domain.order.Order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiscountServiceImpl implements DiscountService {
    private DiscountProvider provider;

    public DiscountServiceImpl(DiscountProvider provider) {
        this.provider = provider;
    }

    @Override
    public Double calculateDiscounts(Order order){
        Double discount = 0d;
        for (Discountable discountable :getDiscounts(order)){
            discount += discountable.calculate();
        }
        order.setFinalPrice(order.getFinalPrice()-discount);
        return discount;
    }

    private List<Discountable> getDiscounts(Order order){
        return provider.getDiscountsForOrder(order);
    }

    public DiscountProvider getProvider() {
        return provider;
    }

    public void setProvider(DiscountProvider provider) {
        this.provider = provider;
    }
}
