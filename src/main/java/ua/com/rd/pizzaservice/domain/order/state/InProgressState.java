package ua.com.rd.pizzaservice.domain.order.state;

public class InProgressState extends State {
    @Override
    public void doAction() {
        System.out.println("Order in progress.");
    }

    @Override
    public boolean equals(State other) {
        return other instanceof InProgressState;
    }
}
