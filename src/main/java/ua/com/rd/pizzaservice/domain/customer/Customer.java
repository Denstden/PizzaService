package ua.com.rd.pizzaservice.domain.customer;

import ua.com.rd.pizzaservice.domain.address.Address;
import ua.com.rd.pizzaservice.domain.order.Order;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "CUSTOMERS")
@Entity
public class Customer {
    @Id
    @SequenceGenerator(name="CUSTOMER_SEQ", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="CUSTOMER_SEQ")
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @Column(name = "CUSTOMER_NAME")
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();

    @Version
    private Integer version;

    public Customer(){
    }

    public Customer(Long id, String name, Address address) {
        addresses.add(address);
        this.id = id;
        this.name = name;
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

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address){
        addresses.add(address);
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{"
                + "id=" + id
                + ", name='" + name
                + ", address=" + addresses
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        if (id!=null && customer.getId()!=null && id.equals(customer.getId())){
            return true;
        }

        if (!name.equals(customer.name)) return false;
        if (!addresses.equals(customer.addresses)) return false;
        return orders.equals(customer.orders);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + addresses.hashCode();
        result = 31 * result + orders.hashCode();
        return result;
    }
}
