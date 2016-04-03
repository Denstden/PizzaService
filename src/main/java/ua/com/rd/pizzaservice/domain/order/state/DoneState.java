package ua.com.rd.pizzaservice.domain.order.state;

public class DoneState extends State {
    @Override
    public void doAction() {
        System.out.println("Order was done.");
    }

    @Override
    public boolean equals(State other) {
        return other instanceof DoneState;
    }
}
