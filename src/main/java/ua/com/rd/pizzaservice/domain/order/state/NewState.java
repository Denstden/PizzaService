package ua.com.rd.pizzaservice.domain.order.state;

public class NewState extends State {
    @Override
    public void doAction() {
        System.out.println("Order was created.");
    }

    @Override
    public boolean equals(State other) {
        return other instanceof NewState;
    }
}
