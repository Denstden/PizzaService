package ua.com.rd.pizzaservice.domain.order.state;

public abstract class State {
    public abstract void doAction();

    public abstract boolean equals(State other);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
