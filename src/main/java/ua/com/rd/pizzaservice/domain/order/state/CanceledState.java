package ua.com.rd.pizzaservice.domain.order.state;

public class CanceledState extends State {
    @Override
    public void doAction() {
        System.out.println("Order was canceled.");
    }

    @Override
    public boolean equals(State other) {
        return other instanceof CanceledState;
    }
}
