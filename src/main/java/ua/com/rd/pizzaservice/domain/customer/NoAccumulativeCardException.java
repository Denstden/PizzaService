package ua.com.rd.pizzaservice.domain.customer;

public class NoAccumulativeCardException extends Exception {
    @Override
    public String getMessage() {
        return "Customer do not have an accumulative card.";
    }
}
