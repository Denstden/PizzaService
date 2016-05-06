package ua.com.rd.pizzaservice.exception;

public class NoAccumulativeCardException extends Exception {
    @Override
    public String getMessage() {
        return "Customer do not have an accumulative card.";
    }
}
