package ua.com.rd.pizzaservice.exception;

public class InvalidPizzasCountException extends Exception {
    @Override
    public String getMessage() {
        return "Count of pizzas in order should be more than 0 and less than 11.";
    }
}
