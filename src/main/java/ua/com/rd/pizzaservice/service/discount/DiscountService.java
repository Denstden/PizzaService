package ua.com.rd.pizzaservice.service.discount;

import ua.com.rd.pizzaservice.domain.order.Order;

public interface DiscountService {
    Double calculateDiscounts(Order order);
}
