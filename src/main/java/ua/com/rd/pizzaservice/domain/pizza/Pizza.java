package ua.com.rd.pizzaservice.domain.pizza;

import javax.persistence.*;

@Entity
@Table(name = "PIZZAS")
public class Pizza {
    @Id
    //@SequenceGenerator(name="PIZZA_SEQ", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy= GenerationType.IDENTITY/*, generator="PIZZA_SEQ"*/)
    @Column(name = "PIZZA_ID")
    private Long id;

    @Column(name = "PIZZA_NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "PIZZA_TYPE")
    @Enumerated(EnumType.STRING)
    private PizzaType type;

    @Version
    private Integer version;

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
                "', price=" + price +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pizza pizza = (Pizza) o;
        if (id!=null && pizza.getId()!=null && id.equals(pizza.getId())){
            return true;
        }

        if (!name.equals(pizza.name)) return false;
        if (!price.equals(pizza.price)) return false;
        return type == pizza.type;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
