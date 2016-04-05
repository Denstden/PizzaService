package ua.com.rd.pizzaservice.domain.pizza;

public class Pizza {
    private Long id;
    private String name;
    private Double price;
    private PizzaType type;

    public Pizza() {
    }

    public Pizza(Long id, String name, Double price, PizzaType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Pizza(String name, Double price, PizzaType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PizzaType getType() {
        return type;
    }

    public void setType(PizzaType type) {
        this.type = type;
    }

    public enum PizzaType {
        VEGETARIAN,
        SEA,
        MEAT
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
