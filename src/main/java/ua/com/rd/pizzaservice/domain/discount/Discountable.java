package ua.com.rd.pizzaservice.domain.discount;

import ua.com.rd.pizzaservice.domain.order.Order;

public interface Discountable {
    void setOrder(Order order);
    Double calculate();
}
