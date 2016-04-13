package ua.com.rd.pizzaservice.service.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.rd.pizzaservice.domain.discount.Discountable;
import ua.com.rd.pizzaservice.domain.order.Order;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountProvider discountProvider;

    public DiscountServiceImpl() {
    }

    public void setDiscountProvider(DiscountProvider discountProvider) {
        this.discountProvider = discountProvider;
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
        return discountProvider.getDiscountsForOrder(order);
    }
}
