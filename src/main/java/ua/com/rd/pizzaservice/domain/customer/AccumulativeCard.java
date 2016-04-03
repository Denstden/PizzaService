package ua.com.rd.pizzaservice.domain.customer;

class AccumulativeCard{
    private Double cash;

    public AccumulativeCard() {
        cash = 0.;
    }

    public Double getCash() {
        return cash;
    }

    public void addCash(Double cash1){
        cash+=cash1;
    }

    @Override
    public String toString() {
        return "AccumulativeCard{" +
                "cash=" + cash +
                '}';
    }
}