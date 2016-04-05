package ua.com.rd.pizzaservice.domain.order;

public class IncorrectStateException extends Exception {
    public IncorrectStateException(String message) {
        super(message);
    }
}
