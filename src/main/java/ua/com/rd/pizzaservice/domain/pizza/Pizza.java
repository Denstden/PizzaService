package ua.com.rd.pizzaservice.domain.pizza;

import javax.persistence.*;

@Entity
@Table(name = "PIZZAS")
public class Pizza {
    @Id
    @SequenceGenerator(name="PIZZA_SEQ", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="PIZZA_SEQ")
    @Column(name = "PIZZA_ID")
    private Long id;

    @Column(name = "PIZZA_NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "PIZZA_TYPE")
    @Enumerated(EnumType.STRING)
    private PizzaType type;

    public Pizza(){
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
